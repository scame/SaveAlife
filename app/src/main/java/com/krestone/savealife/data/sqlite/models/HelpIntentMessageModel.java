package com.krestone.savealife.data.sqlite.models;


public class HelpIntentMessageModel {

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
}
