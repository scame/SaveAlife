package com.krestone.savealife.data.entities.requests;



public class PhoneNumberHolder {

    private String phoneNumber;

    public PhoneNumberHolder() { }

    public PhoneNumberHolder(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
