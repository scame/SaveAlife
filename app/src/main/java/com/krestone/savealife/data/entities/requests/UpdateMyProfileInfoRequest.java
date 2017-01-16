package com.krestone.savealife.data.entities.requests;


import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;

public class UpdateMyProfileInfoRequest {

    private String role = "";

    private String firstName = "";

    private String lastName = "";

    private String medicalQualification = "";

    private String password = "";

    private String newPassword = "";

    public UpdateMyProfileInfoRequest(MyProfileInfoEntity profileInfo) {
        this.role = profileInfo.getRole();
        this.firstName = profileInfo.getFirstName();
        this.lastName = profileInfo.getLastName();
        this.medicalQualification = profileInfo.getMedicalQualification();
        this.password = profileInfo.getPassword();
        this.newPassword = profileInfo.getNewPassword();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMedicalQualification() {
        return medicalQualification;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
