package com.krestone.savealife.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

public class ContactModel implements Parcelable {

    private String id;

    @NonNull // concatenated name (first + last names)
    private String firstName;

    // used only for current user profile (otherwise - null)
    private String lastName;

    private String phoneNumber;

    private String thumbnailUri;

    private Integer status;

    private boolean inApp;

    private boolean isModified;

    public ContactModel() { }

    public ContactModel(ContactModel contactModel) {
        this.id = contactModel.getId() == null ? null : contactModel.getId();
        this.firstName = contactModel.getFirstName();
        this.phoneNumber = contactModel.getPhoneNumber() == null ? null : contactModel.getPhoneNumber();
        this.thumbnailUri = contactModel.getThumbnailUri() == null ? null : contactModel.getThumbnailUri();
        this.inApp = contactModel.isInApp();
        this.isModified = contactModel.isModified();
    }

    public ContactModel(String id, @NonNull String name, String thumbnailUri, String phoneNumber) {
        this.id = id;
        this.firstName = name;
        this.thumbnailUri = thumbnailUri;
        this.phoneNumber = phoneNumber;
    }

    public ContactModel(String id, @NonNull String firstName, @NonNull String lastName,
                        String thumbnailUri, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.thumbnailUri);
        dest.writeValue(this.status);
        dest.writeByte(this.inApp ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isModified ? (byte) 1 : (byte) 0);
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNumber = in.readString();
        this.thumbnailUri = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
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
