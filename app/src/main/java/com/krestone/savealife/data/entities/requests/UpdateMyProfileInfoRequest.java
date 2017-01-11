package com.krestone.savealife.data.entities.requests;



public class UpdateMyProfileInfoRequest {

    private String role;

    private String name;

    private String medicalQualification;

    private String password;

    private String newPassword;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
