//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.08.09 at 03:14:14 PM AZT 
//


package az.pashabank.smsbank3ds_web_service;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the az.pashabank.smsbank3ds_web_service package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: az.pashabank.smsbank3ds_web_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCard3DSMSInfoRequest }
     * 
     */
    public GetCard3DSMSInfoRequest createGetCard3DSMSInfoRequest() {
        return new GetCard3DSMSInfoRequest();
    }

    /**
     * Create an instance of {@link GetCard3DSMSInfoResponse }
     * 
     */
    public GetCard3DSMSInfoResponse createGetCard3DSMSInfoResponse() {
        return new GetCard3DSMSInfoResponse();
    }

    /**
     * Create an instance of {@link Data }
     * 
     */
    public Data createData() {
        return new Data();
    }

    /**
     * Create an instance of {@link ModifyCard3DSecureRequest }
     * 
     */
    public ModifyCard3DSecureRequest createModifyCard3DSecureRequest() {
        return new ModifyCard3DSecureRequest();
    }

    /**
     * Create an instance of {@link ModifyCard3DSecureResponse }
     * 
     */
    public ModifyCard3DSecureResponse createModifyCard3DSecureResponse() {
        return new ModifyCard3DSecureResponse();
    }

    /**
     * Create an instance of {@link ModifyCardSMSBankingRequest }
     * 
     */
    public ModifyCardSMSBankingRequest createModifyCardSMSBankingRequest() {
        return new ModifyCardSMSBankingRequest();
    }

    /**
     * Create an instance of {@link ModifyCardSMSBankingResponse }
     * 
     */
    public ModifyCardSMSBankingResponse createModifyCardSMSBankingResponse() {
        return new ModifyCardSMSBankingResponse();
    }

}
