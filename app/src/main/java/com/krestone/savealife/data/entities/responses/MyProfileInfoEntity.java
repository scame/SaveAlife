package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class MyProfileInfoEntity implements Parcelable {

    private String phoneNumber;

    private String role;

    private String name;

    private String medicalQualification;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.role);
        dest.writeString(this.name);
        dest.writeString(this.medicalQualification);
    }

    public MyProfileInfoEntity() {
    }

    protected MyProfileInfoEntity(Parcel in) {
        this.phoneNumber = in.readString();
        this.role = in.readString();
        this.name = in.readString();
        this.medicalQualification = in.readString();
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
