package az.pashabank.apl.ms.model;

public class Card {

    private String cardNo;
    private String pan;
    private String expiry;
    private boolean pliCard;

    public Card(String cardNo, String pan, String expiry, boolean pliCard) {
        this.cardNo = cardNo;
        this.pan = pan;
        this.expiry = expiry;
        this.pliCard = pliCard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getExpiry() {
        return expiry;
    }

    public void setExpiry(String expiry) {
        this.expiry = expiry;
    }

    public boolean isPliCard() {
        return pliCard;
    }

    public void setPliCard(boolean pliCard) {
        this.pliCard = pliCard;
    }

    @Override
    public String toString() {
        return "Card { " +
                "cardNo = " + cardNo +
                ", pan = " + pan +
                ", expiry = " + expiry +
                ", pliCard = " + pliCard +
                " }";
    }

}
