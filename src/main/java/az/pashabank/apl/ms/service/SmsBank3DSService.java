package az.pashabank.apl.ms.service;

import az.pashabank.apl.ms.dao.SmsBank3DSDao;
import az.pashabank.apl.ms.enums.ResultCode;
import az.pashabank.apl.ms.model.Card3DSMSInfo;
import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;
import az.pashabank.apl.ms.validator.SmsBank3DSValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsBank3DSService {

    private static final String SUCCESS = "SUCCESS: Operation is successful";

    @Autowired
    private SmsBank3DSValidator validator;

    @Autowired
    private SmsBank3DSDao smsBank3DSDao;

    public OperationResponse<Card3DSMSInfo> getCard3DSMSInfo(String pan, String username) {
        OperationResponse<Card3DSMSInfo> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isPanValid(pan, operationResponse)) {
            processCard3DSMSInfoRequest(pan, username, operationResponse);
        }
        return operationResponse;
    }

    private void processCard3DSMSInfoRequest(String pan, String username, OperationResponse operationResponse) {
        Card3DSMSInfo card3DSMSInfo = smsBank3DSDao.getCard3DSMSInfo(pan, username, operationResponse);
        if (card3DSMSInfo != null) {
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
            operationResponse.setData(card3DSMSInfo);
        }
    }

    public OperationResponse<String> modifyCard3DSecure(ModifyCardRequest request, String username) {
        OperationResponse<String> operationResponse = new OperationResponse<>(ResultCode.ERROR);
        if (validator.isRequestValid(request, false, operationResponse)) {
            processModifyCard3DSecureRequest(request, username, operationResponse);
        }
        return operationResponse;
    }

    private void processModifyCard3DSecureRequest(ModifyCardRequest request, String username, OperationResponse operationResponse) {
        String resultCode = smsBank3DSDao.modifyCard3DSecure(request, username, operationResponse);
        if (resultCode != null && !resultCode.trim().isEmpty()) {
            operationResponse.setData(resultCode);
            operationResponse.setCode(ResultCode.OK);
            operationResponse.setMessage(SUCCESS);
        }
    }

    public OperationResponse modifyCardSMSBanking(ModifyCardRequest request, String username) {
        OperationResponse operationResponse = new OperationResponse(ResultCode.ERROR);
        if (validator.isRequestValid(request, true, operationResponse)) {
            smsBank3DSDao.modifyCardSMSBanking(request, username, operationResponse);
        }
        return operationResponse;
    }

    public boolean isSoapUserAuthenticated(String username, String encryptedPassword, OperationResponse operationResponse) {
        return smsBank3DSDao.isSoapUserAuthenticated(username, encryptedPassword, operationResponse);
    }

}