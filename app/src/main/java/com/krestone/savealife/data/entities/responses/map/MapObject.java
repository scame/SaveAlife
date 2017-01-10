package com.krestone.savealife.data.entities.responses.map;


import android.os.Parcel;
import android.os.Parcelable;

public class MapObject implements Parcelable {

    private double longitude;

    private double latitude;

    private boolean isSos;

    private String phoneNumber;

    private String role;

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setSos(boolean sos) {
        isSos = sos;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public boolean isSos() {
        return isSos;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRole() {
        return role;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.longitude);
        dest.writeDouble(this.latitude);
        dest.writeByte(this.isSos ? (byte) 1 : (byte) 0);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.role);
    }

    public MapObject() {
    }

    protected MapObject(Parcel in) {
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.isSos = in.readByte() != 0;
        this.phoneNumber = in.readString();
        this.role = in.readString();
    }

    public static final Parcelable.Creator<MapObject> CREATOR = new Parcelable.Creator<MapObject>() {
        @Override
        public MapObject createFromParcel(Parcel source) {
            return new MapObject(source);
        }

        @Override
        public MapObject[] newArray(int size) {
            return new MapObject[size];
        }
    };
}
