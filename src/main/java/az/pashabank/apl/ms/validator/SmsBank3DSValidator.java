package az.pashabank.apl.ms.validator;

import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;
import org.springframework.stereotype.Component;

@Component
public class SmsBank3DSValidator {

    private static final String WRONG_PAN_1 = "WRONG PAN: PAN is null";
    private static final String WRONG_PAN_2 = "WRONG PAN: PAN is empty";
    private static final String WRONG_MOBILE_NO_1 = "WRONG MOBILE NO: Mobile number is null";
    private static final String WRONG_MOBILE_NO_2 = "WRONG MOBILE NO: Mobile number is empty";
    private static final String WRONG_LANG_1 = "WRONG LANG: Language is null";
    private static final String WRONG_LANG_2 = "WRONG LANG: Language is empty";

    private static final String WRONG_REQUEST = "WRONG REQUEST: Mandatory request parameter is null";

    public boolean isPanValid(String pan, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(pan, operationResponse, WRONG_PAN_1, WRONG_PAN_2);
    }

    private boolean isMobileNoValid(String mobileNo, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(mobileNo, operationResponse, WRONG_MOBILE_NO_1, WRONG_MOBILE_NO_2);
    }

    private boolean isLangValid(String lang, OperationResponse operationResponse) {
        return !isStringNullOrEmpty(lang, operationResponse, WRONG_LANG_1, WRONG_LANG_2);
    }

    public boolean isRequestValid(ModifyCardRequest request, boolean isSmsBank, OperationResponse operationResponse) {
        boolean isValid = true;
        if (request == null) {
            isValid = false;
            operationResponse.setMessage(WRONG_REQUEST);
        } else if (
                !isPanValid(request.getPan(), operationResponse) ||
                        !isMobileNoValid(request.getMobileNumber(), operationResponse)
        ) {
            isValid = false;
        } else if (
                isSmsBank && !isLangValid(request.getLang(), operationResponse)
        ) {
            isValid = false;
        }
        return isValid;
    }

    private boolean isStringNullOrEmpty(String field, OperationResponse operationResponse, String msg1, String msg2) {
        boolean result = false;
        if (field == null) {
            result = true;
            operationResponse.setMessage(msg1);
        } else if (field.trim().isEmpty()) {
            result = true;
            operationResponse.setMessage(msg2);
        }
        return result;
    }

}
