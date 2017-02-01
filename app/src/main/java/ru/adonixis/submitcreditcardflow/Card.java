package ru.adonixis.submitcreditcardflow;

public class Card {

    private String cardNumber;
    private String expiredDate;
    private String cardHolder;
    private String cvvCode;

    public Card() {
    }

    public Card(String cardNumber, String expiredDate, String cardHolder, String cvvCode) {
        this.cardNumber = cardNumber;
        this.expiredDate = expiredDate;
        this.cardHolder = cardHolder;
        this.cvvCode = cvvCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getCvvCode() {
        return cvvCode;
    }

    public void setCvvCode(String cvvCode) {
        this.cvvCode = cvvCode;
    }

    @Override
    public String toString() {
        return "Card info:\r\n" +
                "Card number = " + cardNumber + "\r\n" +
                "Expired date = " + expiredDate + "\r\n" +
                "Card holder = " + cardHolder + "\r\n" +
                "CVV code = " + cvvCode;
    }
}
