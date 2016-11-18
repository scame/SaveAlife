package com.krestone.savealife.presentation.models;



public class ContactModel {

    private String id;

    private String name;

    private String homeNumber;

    private String workNumber;

    private String mobileNumber;

    private String thumbnailUri;

    private boolean inApp;

    private boolean inEmergencyList;

    public ContactModel() { }

    public ContactModel(String id, String name, String thumbnailUri) {
        this.id = id;
        this.name = name;
        this.thumbnailUri = thumbnailUri;
    }

    public boolean isInEmergencyList() {
        return inEmergencyList;
    }

    public boolean isInApp() {
        return inApp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public void setInEmergencyList(boolean inEmergencyList) {
        this.inEmergencyList = inEmergencyList;
    }

    public void setInApp(boolean inApp) {
        this.inApp = inApp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
