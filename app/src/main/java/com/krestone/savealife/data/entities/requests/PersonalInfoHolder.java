package com.krestone.savealife.data.entities.requests;



public class PersonalInfoHolder {

    private String firstName;

    private String lastName;

    private String password;

    private String medicalQualification;

    private String phoneNumber;

    public PersonalInfoHolder() { }

    public PersonalInfoHolder(String firstName, String lastName,
                              String password, String medicalQualification,
                              String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.medicalQualification = medicalQualification;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
