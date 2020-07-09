package az.pashabank.apl.ms.model;

public class ModifyCardRequest {

    String pan;
    String mobileNumber;
    boolean enable;
    String lang;

    public ModifyCardRequest() { }

    public ModifyCardRequest(String pan, String mobileNumber, boolean enable) {
        this.pan = pan;
        this.mobileNumber = mobileNumber;
        this.enable = enable;
    }

    public ModifyCardRequest(String pan, String mobileNumber, boolean enable, String lang) {
        this.pan = pan;
        this.mobileNumber = mobileNumber;
        this.enable = enable;
        this.lang = lang;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "ModifyCardRequest { "
                + "pan=" + pan + ", "
                + "mobileNumber=" + mobileNumber + ", "
                + "enable=" + enable + ", "
                + "lang=" + lang + " }";
    }

}