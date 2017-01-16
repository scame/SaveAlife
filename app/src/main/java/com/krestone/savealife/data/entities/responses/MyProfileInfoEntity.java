package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class MyProfileInfoEntity implements Parcelable {

    private String phoneNumber = "";

    private String role = "";

    private String firstName = "";

    private String lastName = "";

    private String medicalQualification = "";

    private String password = "";

    private String newPassword = "";

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    public String getMedicalQualification() {
        return medicalQualification;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.role);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.medicalQualification);
        dest.writeString(this.password);
        dest.writeString(this.newPassword);
    }

    public MyProfileInfoEntity() {
    }

    protected MyProfileInfoEntity(Parcel in) {
        this.phoneNumber = in.readString();
        this.role = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.medicalQualification = in.readString();
        this.password = in.readString();
        this.newPassword = in.readString();
    }

    public static final Creator<MyProfileInfoEntity> CREATOR = new Creator<MyProfileInfoEntity>() {
        @Override
        public MyProfileInfoEntity createFromParcel(Parcel source) {
            return new MyProfileInfoEntity(source);
        }

        @Override
        public MyProfileInfoEntity[] newArray(int size) {
            return new MyProfileInfoEntity[size];
        }
    };
}
