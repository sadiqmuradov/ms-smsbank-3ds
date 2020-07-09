package az.pashabank.apl.ms.controller;

import az.pashabank.apl.ms.logger.MainLogger;
import az.pashabank.apl.ms.model.Card3DSMSInfo;
import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;
import az.pashabank.apl.ms.model.URL;
import az.pashabank.apl.ms.service.SmsBank3DSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Validated
@Api("SMS Banking 3DS Controller: SMS banking 3DS REST operations")
public class SmsBank3DSController {

    private static final MainLogger LOGGER = MainLogger.getLogger(SmsBank3DSController.class);

    @Autowired
    private SmsBank3DSService smsBank3dsService;

    @ApiOperation("Get 3DS SMS info of a card")
    @GetMapping(URL.GET_CARD_3DSMS_INFO)
    public OperationResponse<Card3DSMSInfo> getCard3DSMSInfo(@PathVariable final String pan, @AuthenticationPrincipal final UserDetails userDetails) {
        OperationResponse<Card3DSMSInfo> response = smsBank3dsService.getCard3DSMSInfo(pan, userDetails.getUsername());
        LOGGER.info("getCard3DSMsInfo. pan: {}, userDetails: {}, response: {}", pan, userDetails, response);
        return response;
    }

    @ApiOperation("Modify card using 3D Secure")
    @PostMapping(URL.POST_MODIFY_CARD_3D_SECURE)
    public OperationResponse<String> modifyCard3DSecure(@RequestBody final ModifyCardRequest request, @AuthenticationPrincipal final UserDetails userDetails) {
        OperationResponse<String> response = smsBank3dsService.modifyCard3DSecure(request, userDetails.getUsername());
        LOGGER.info("modifyCard3DSecure. request: {}, userDetails: {}, response: {}", request, userDetails, response);
        return response;
    }

    @ApiOperation("Modify card using SMS Banking")
    @PostMapping(URL.POST_MODIFY_CARD_SMS_BANKING)
    public OperationResponse modifyCardSMSBanking(@RequestBody final ModifyCardRequest request, @AuthenticationPrincipal final UserDetails userDetails) {
        OperationResponse response = smsBank3dsService.modifyCardSMSBanking(request, userDetails.getUsername());
        LOGGER.info("modifyCardSMSBanking. request: {}, userDetails: {}, response: {}", request, userDetails, response);
        return response;
    }

}