package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class SomeoneProfileEntity implements Parcelable {

    private String phoneNumber;

    private String firstName;

    private String lastName;

    private String role;

    private String medicalQualification;

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public String getRole() {
        return role;
    }

    public String getMedicalQualification() {
        return medicalQualification;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.phoneNumber);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.role);
        dest.writeString(this.medicalQualification);
    }

    public SomeoneProfileEntity() {
    }

    protected SomeoneProfileEntity(Parcel in) {
        this.phoneNumber = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
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
