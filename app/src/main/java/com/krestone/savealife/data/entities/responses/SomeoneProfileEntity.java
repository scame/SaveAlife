package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class SomeoneProfileEntity implements Parcelable {

    private String phoneNumber;

    private String name;

    private String role;

    private String medicalQualification;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setMedicalQualification(String medicalQualification) {
        this.medicalQualification = medicalQualification;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getMedicalQualification() {
        return medicalQualification;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.name);
        dest.writeString(this.role);
        dest.writeString(this.medicalQualification);
    }

    public SomeoneProfileEntity() {
    }

    protected SomeoneProfileEntity(Parcel in) {
        this.phoneNumber = in.readString();
        this.name = in.readString();
        this.role = in.readString();
        this.medicalQualification = in.readString();
    }

    public static final Creator<SomeoneProfileEntity> CREATOR = new Creator<SomeoneProfileEntity>() {
        @Override
        public SomeoneProfileEntity createFromParcel(Parcel source) {
            return new SomeoneProfileEntity(source);
        }

        @Override
        public SomeoneProfileEntity[] newArray(int size) {
            return new SomeoneProfileEntity[size];
        }
    };
}
