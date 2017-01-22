package com.krestone.savealife.data.sqlite.models;


import android.os.Parcel;

public class HelpIntentMessageModel extends AbstractMessage {

    private String firstName;

    private String lastName;

    private String time;

    private String phoneNumber;

    private double distance;

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

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int getMessageType() {
        return AbstractMessage.HELP_INTENT_MESSAGE;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.time);
        dest.writeString(this.phoneNumber);
        dest.writeDouble(this.distance);
        dest.writeByte(this.isStart ? (byte) 1 : (byte) 0);
        dest.writeInt(this.globalMessageType);
    }

    public HelpIntentMessageModel() {
    }

    protected HelpIntentMessageModel(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.time = in.readString();
        this.phoneNumber = in.readString();
        this.distance = in.readDouble();
        this.isStart = in.readByte() != 0;
        this.globalMessageType = in.readInt();
    }

    public static final Creator<HelpIntentMessageModel> CREATOR = new Creator<HelpIntentMessageModel>() {
        @Override
        public HelpIntentMessageModel createFromParcel(Parcel source) {
            return new HelpIntentMessageModel(source);
        }

        @Override
        public HelpIntentMessageModel[] newArray(int size) {
            return new HelpIntentMessageModel[size];
        }
    };
}
