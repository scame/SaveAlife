package com.krestone.savealife.data.entities.requests;



public class VerificationHolder {

    private int verificationCode;

    private int phoneNumber;

    public void setVerificationCode(int verificationCode) {
        this.verificationCode = verificationCode;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getVerificationCode() {
        return verificationCode;
    }
}
