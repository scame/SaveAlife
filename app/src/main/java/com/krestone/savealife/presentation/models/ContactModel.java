package com.krestone.savealife.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {

    private String id;

    private String name;

    private String homeNumber;

    private String workNumber;

    private String mobileNumber;

    private String thumbnailUri;

    private boolean inApp;

    private boolean inEmergencyList;

    public ContactModel() { }

    public ContactModel(ContactModel contactModel) {
        this.id = contactModel.getId() == null ? null : contactModel.getId();
        this.name = contactModel.getName() == null ? null : contactModel.getName();
        this.homeNumber = contactModel.getHomeNumber() == null ? null : contactModel.getHomeNumber();
        this.workNumber = contactModel.getWorkNumber() == null ? null : contactModel.getWorkNumber();
        this.mobileNumber = contactModel.getMobileNumber() == null ? null : contactModel.getMobileNumber();
        this.thumbnailUri = contactModel.getThumbnailUri() == null ? null : contactModel.getThumbnailUri();
        this.inApp = contactModel.isInApp();
        this.inEmergencyList = contactModel.isInEmergencyList();
    }

    public ContactModel(String id, String name, String thumbnailUri, String mobileNumber) {
        this.id = id;
        this.name = name;
        this.thumbnailUri = thumbnailUri;
        this.mobileNumber = mobileNumber;
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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;

        if (obj.getClass().equals(this.getClass())) {
            ContactModel contact = (ContactModel) obj;

            return  (contact.getId() == null ? this.id == null : contact.getId().equals(this.id)) &&
                    (contact.getName() == null ? this.name == null : contact.getName().equals(this.name)) &&
                    (contact.getHomeNumber() == null ? this.homeNumber == null : contact.getHomeNumber().equals(this.homeNumber)) &&
                    (contact.getWorkNumber() == null ? this.workNumber == null : contact.getWorkNumber().equals(this.workNumber)) &&
                    (contact.getMobileNumber() == null ? this.mobileNumber == null : contact.getMobileNumber().equals(this.mobileNumber)) &&
                    (contact.getThumbnailUri() == null ? this.thumbnailUri == null : contact.getThumbnailUri().equals(this.thumbnailUri)) &&
                    (contact.isInApp() == this.inApp && contact.isInEmergencyList() == this.inEmergencyList);
        }
        return false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.homeNumber);
        dest.writeString(this.workNumber);
        dest.writeString(this.mobileNumber);
        dest.writeString(this.thumbnailUri);
        dest.writeByte(this.inApp ? (byte) 1 : (byte) 0);
        dest.writeByte(this.inEmergencyList ? (byte) 1 : (byte) 0);
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.homeNumber = in.readString();
        this.workNumber = in.readString();
        this.mobileNumber = in.readString();
        this.thumbnailUri = in.readString();
        this.inApp = in.readByte() != 0;
        this.inEmergencyList = in.readByte() != 0;
    }

    public static final Creator<ContactModel> CREATOR = new Creator<ContactModel>() {
        @Override
        public ContactModel createFromParcel(Parcel source) {
            return new ContactModel(source);
        }

        @Override
        public ContactModel[] newArray(int size) {
            return new ContactModel[size];
        }
    };
}
