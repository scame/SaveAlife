package com.krestone.savealife.data.entities.requests;



public class PersonalInfoHolder {

    private String name;

    private String password;

    private String medicalQualification;

    private String phoneNumber;

    // it's a code sent by the server
    private String currentToken;

    public PersonalInfoHolder() { }

    public PersonalInfoHolder(String name, String password, String medicalQualification,
                              String phoneNumber, String currentToken) {
        this.name = name;
        this.password = password;
        this.medicalQualification = medicalQualification;
        this.phoneNumber = phoneNumber;
        this.currentToken = currentToken;
    }

    public String getCurrentToken() {
        return currentToken;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getMedicalQualification() {
        return medicalQualification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
    }

    public void setCurrentToken(String currentToken) {
        this.currentToken = currentToken;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
