package com.krestone.savealife.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;

public class ContactModel implements Parcelable {

    private String id;

    private String firstName;

    private String homeNumber;

    private String workNumber;

    private String phoneNumber;

    private String thumbnailUri;

    private boolean inApp;

    private boolean isModified;

    public ContactModel() { }

    public ContactModel(ContactModel contactModel) {
        this.id = contactModel.getId() == null ? null : contactModel.getId();
        this.firstName = contactModel.getName() == null ? null : contactModel.getName();
        this.homeNumber = contactModel.getHomeNumber() == null ? null : contactModel.getHomeNumber();
        this.workNumber = contactModel.getWorkNumber() == null ? null : contactModel.getWorkNumber();
        this.phoneNumber = contactModel.getPhoneNumber() == null ? null : contactModel.getPhoneNumber();
        this.thumbnailUri = contactModel.getThumbnailUri() == null ? null : contactModel.getThumbnailUri();
        this.inApp = contactModel.isInApp();
        this.isModified = contactModel.isModified();
    }

    public ContactModel(String id, String name, String thumbnailUri, String phoneNumber) {
        this.id = id;
        this.firstName = name;
        this.thumbnailUri = thumbnailUri;
        this.phoneNumber = phoneNumber;
    }

    public boolean isModified() {
        return isModified;
    }

    public boolean isInApp() {
        return inApp;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return firstName;
    }

    public String getHomeNumber() {
        return homeNumber;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }

    public void setModified(boolean modified) {
        this.isModified = modified;
    }

    public void setInApp(boolean inApp) {
        this.inApp = inApp;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.firstName = name;
    }

    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    public void setWorkNumber(String workNumber) {
        this.workNumber = workNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;

        if (obj.getClass().equals(this.getClass())) {
            ContactModel contact = (ContactModel) obj;

            return  (contact.getId() == null ? this.id == null : contact.getId().equals(this.id)) &&
                    (contact.getName() == null ? this.firstName == null : contact.getName().equals(this.firstName)) &&
                    (contact.getHomeNumber() == null ? this.homeNumber == null : contact.getHomeNumber().equals(this.homeNumber)) &&
                    (contact.getWorkNumber() == null ? this.workNumber == null : contact.getWorkNumber().equals(this.workNumber)) &&
                    (contact.getPhoneNumber() == null ? this.phoneNumber == null : contact.getPhoneNumber().equals(this.phoneNumber)) &&
                    (contact.getThumbnailUri() == null ? this.thumbnailUri == null : contact.getThumbnailUri().equals(this.thumbnailUri));
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
        dest.writeString(this.firstName);
        dest.writeString(this.homeNumber);
        dest.writeString(this.workNumber);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.thumbnailUri);
        dest.writeByte(this.inApp ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isModified ? (byte) 1 : (byte) 0);
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.homeNumber = in.readString();
        this.workNumber = in.readString();
        this.phoneNumber = in.readString();
        this.thumbnailUri = in.readString();
        this.inApp = in.readByte() != 0;
        this.isModified = in.readByte() != 0;
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
