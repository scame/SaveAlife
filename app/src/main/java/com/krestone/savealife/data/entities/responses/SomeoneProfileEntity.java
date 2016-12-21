package com.krestone.savealife.data.entities.responses;


public class SomeoneProfileEntity {

    private String phoneNumber;

    private boolean enable;

    private String email;

    private String firstName;

    private String lastName;

    private String role;

    private String medicalQualification;

    private String sosTime;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
    }

    public void setSosTime(String sosTime) {
        this.sosTime = sosTime;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getRole() {
        return role;
    }

    public String getMedicalQualification() {
        return medicalQualification;
    }

    public String getSosTime() {
        return sosTime;
    }
}
