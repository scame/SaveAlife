package com.krestone.savealife.data.entities.responses;


public class SomeoneProfileEntity {

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String role;

    private String medicalQualification;

    private String sosTime;

    private boolean sos;

    public boolean isSos() {
        return sos;
    }

    public void setSos(boolean sos) {
        this.sos = sos;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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
