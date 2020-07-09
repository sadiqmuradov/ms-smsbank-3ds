package az.pashabank.apl.ms.model;

public class Card3DSMSInfo {

    private String msisdn;
    private String state;
    private String fee;
    private String secure;
    private String lang;

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Card3DSMSInfo{" +
                "msisdn='" + msisdn + '\'' +
                ", state='" + state + '\'' +
                ", fee='" + fee + '\'' +
                ", secure='" + secure + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }
}
