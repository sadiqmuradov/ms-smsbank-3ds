/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package az.pashabank.apl.ms.dao;

import az.pashabank.apl.ms.model.Card3DSMSInfo;
import az.pashabank.apl.ms.model.ModifyCardRequest;
import az.pashabank.apl.ms.model.OperationResponse;

public interface ISmsBank3DSDao {

    Card3DSMSInfo getCard3DSMSInfo(String pan, String username, OperationResponse operationResponse);
    String modifyCard3DSecure(ModifyCardRequest request, String username, OperationResponse operationResponse);
    void modifyCardSMSBanking(ModifyCardRequest request, String username, OperationResponse operationResponse);
    boolean isSoapUserAuthenticated(String username, String encryptedPassword, OperationResponse operationResponse);

}
