package com.krestone.savealife.presentation.models;



public class ContactModel {

    private String id;

    private String name;

    private int homeNumber;

    private int workNumber;

    private int mobileNumber;

    public ContactModel() { }

    public ContactModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomeNumber(int homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setWorkNumber(int workNumber) {
        this.workNumber = workNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHomeNumber() {
        return homeNumber;
    }

    public int getWorkNumber() {
        return workNumber;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }
}
