package com.krestone.savealife.data.entities.requests;



public class PasswordMatchingRequest {

    private String password;

    private String phoneNumber;

    public PasswordMatchingRequest() { }

    public PasswordMatchingRequest(String password, String phoneNumber) {
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
