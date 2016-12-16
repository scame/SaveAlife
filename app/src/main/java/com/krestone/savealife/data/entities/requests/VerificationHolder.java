package com.krestone.savealife.data.entities.requests;


public class VerificationHolder {

    private String currentToken;

    private String phoneNumber;

    public VerificationHolder() { }

    public VerificationHolder(String currentToken, String phoneNumber) {
        this.currentToken = currentToken;
        this.phoneNumber = phoneNumber;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getCurrentToken() {
        return currentToken;
    }
}
