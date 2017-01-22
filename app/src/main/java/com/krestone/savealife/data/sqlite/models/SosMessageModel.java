package com.krestone.savealife.data.sqlite.models;


import android.os.Parcel;

public class SosMessageModel extends AbstractMessage {

    private double latitude;

    private double longitude;

    private String message;

    private String firstName;

    private String lastName;

    private String time;

    private String phoneNumber;

    private boolean isStart;

    private int globalMessageType;

    public int getGlobalMessageType() {
        return globalMessageType;
    }

    public void setGlobalMessageType(int globalMessageType) {
        this.globalMessageType = globalMessageType;
    }

    public boolean isStart() {
        return isStart;
    }

    public void setStart(boolean start) {
        isStart = start;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int getMessageType() {
        return AbstractMessage.SOS_MESSAGE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeString(this.message);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.time);
        dest.writeString(this.phoneNumber);
        dest.writeByte(this.isStart ? (byte) 1 : (byte) 0);
        dest.writeInt(this.globalMessageType);
    }

    public SosMessageModel() {
    }

    protected SosMessageModel(Parcel in) {
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.message = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.time = in.readString();
        this.phoneNumber = in.readString();
        this.isStart = in.readByte() != 0;
        this.globalMessageType = in.readInt();
    }

    public static final Creator<SosMessageModel> CREATOR = new Creator<SosMessageModel>() {
        @Override
        public SosMessageModel createFromParcel(Parcel source) {
            return new SosMessageModel(source);
        }

        @Override
        public SosMessageModel[] newArray(int size) {
            return new SosMessageModel[size];
        }
    };
}
