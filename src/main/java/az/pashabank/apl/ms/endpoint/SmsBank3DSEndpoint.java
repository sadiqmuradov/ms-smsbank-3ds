/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.endpoint;

import az.pashabank.apl.ms.logger.MainLogger;
import az.pashabank.apl.ms.model.Card3DSMSInfo;
import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;
import az.pashabank.apl.ms.service.SmsBank3DSService;
import az.pashabank.smsbank3ds_web_service.Code;
import az.pashabank.smsbank3ds_web_service.Data;
import az.pashabank.smsbank3ds_web_service.GetCard3DSMSInfoRequest;
import az.pashabank.smsbank3ds_web_service.GetCard3DSMSInfoResponse;
import az.pashabank.smsbank3ds_web_service.ModifyCard3DSecureRequest;
import az.pashabank.smsbank3ds_web_service.ModifyCard3DSecureResponse;
import az.pashabank.smsbank3ds_web_service.ModifyCardSMSBankingRequest;
import az.pashabank.smsbank3ds_web_service.ModifyCardSMSBankingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class SmsBank3DSEndpoint {

    private static final MainLogger LOGGER = MainLogger.getLogger(SmsBank3DSEndpoint.class);

    private static final String NAMESPACE_URI = "http://pashabank.az/smsbank3ds-web-service";

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private SmsBank3DSService smsBank3dsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCard3DSMSInfoRequest")
    @ResponsePayload
    public GetCard3DSMSInfoResponse getCard3DSMSInfo(@RequestPayload final GetCard3DSMSInfoRequest request) {
        GetCard3DSMSInfoResponse getCard3DSMSInfoResponse = null;
        String username = getAuthenticatedUser();
        if (username != null && !username.trim().isEmpty()) {
            OperationResponse<Card3DSMSInfo> operationResponse = smsBank3dsService.getCard3DSMSInfo(request.getPan(), username);
            LOGGER.info("getCard3DSMsInfo. pan: {}, username: {}, operationResponse: {}", request.getPan(), username, operationResponse);

            getCard3DSMSInfoResponse = new GetCard3DSMSInfoResponse();

            getCard3DSMSInfoResponse.setCode(Code.fromValue(operationResponse.getCode().name()));
            getCard3DSMSInfoResponse.setMessage(operationResponse.getMessage());

            Card3DSMSInfo card3DSMSInfo = operationResponse.getData();
            Data data = new Data();

            data.setMsisdn(card3DSMSInfo.getMsisdn());
            data.setState(card3DSMSInfo.getState());
            data.setFee(card3DSMSInfo.getFee());
            data.setSecure(card3DSMSInfo.getSecure());
            data.setLang(card3DSMSInfo.getLang());
            getCard3DSMSInfoResponse.setData(data);
        }
        return getCard3DSMSInfoResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyCard3DSecureRequest")
    @ResponsePayload
    public ModifyCard3DSecureResponse modifyCard3DSecure(@RequestPayload final ModifyCard3DSecureRequest request) {
        ModifyCard3DSecureResponse modifyCard3DSecureResponse = null;
        String username = getAuthenticatedUser();
        if (username != null && !username.trim().isEmpty()) {
            ModifyCardRequest modifyCardRequest = new ModifyCardRequest(request.getPan(), request.getMobileNumber(), request.isEnable());
            OperationResponse<String> operationResponse = smsBank3dsService.modifyCard3DSecure(modifyCardRequest, username);
            LOGGER.info("modifyCard3DSecure. modifyCardRequest: {}, username: {}, operationResponse: {}", modifyCardRequest, username, operationResponse);

            modifyCard3DSecureResponse = new ModifyCard3DSecureResponse();
            modifyCard3DSecureResponse.setCode(Code.fromValue(operationResponse.getCode().name()));
            modifyCard3DSecureResponse.setMessage(operationResponse.getMessage());
            modifyCard3DSecureResponse.setData(operationResponse.getData());
        }
        return modifyCard3DSecureResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "modifyCardSMSBankingRequest")
    @ResponsePayload
    public ModifyCardSMSBankingResponse modifyCardSMSBanking(@RequestPayload final ModifyCardSMSBankingRequest request) {
        ModifyCardSMSBankingResponse modifyCardSMSBankingResponse = null;
        String username = getAuthenticatedUser();
        if (username != null && !username.trim().isEmpty()) {
            ModifyCardRequest modifyCardRequest = new ModifyCardRequest(
                    request.getPan(), request.getMobileNumber(), request.isEnable(), request.getLang()
            );
            OperationResponse operationResponse = smsBank3dsService.modifyCardSMSBanking(modifyCardRequest, username);
            LOGGER.info("modifyCardSMSBanking. modifyCardRequest: {}, username: {}, operationResponse: {}", modifyCardRequest, username, operationResponse);

            modifyCardSMSBankingResponse = new ModifyCardSMSBankingResponse();
            modifyCardSMSBankingResponse.setCode(Code.fromValue(operationResponse.getCode().name()));
            modifyCardSMSBankingResponse.setMessage(operationResponse.getMessage());
        }
        return modifyCardSMSBankingResponse;
    }

    private String getAuthenticatedUser() {
        String username = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            username = authentication.getName();
        }
        return username;
    }

}
