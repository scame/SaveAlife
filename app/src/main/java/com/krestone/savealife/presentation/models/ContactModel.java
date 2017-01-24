package com.krestone.savealife.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.krestone.savealife.data.sync.states.DataStates;

public class ContactModel implements Parcelable {

    private String id;

    @NonNull // concatenated name (first + last names)
    private String firstName;

    // used only for current user profile (otherwise - null)
    private String lastName;

    private String number;

    private String thumbnailUri;

    private Integer status;

    private DataStates dataState;

    private Boolean inApp;

    public ContactModel() { }

    public ContactModel(ContactModel contactModel) {
        this.id = contactModel.getId();
        this.firstName = contactModel.getFirstName();
        this.number = contactModel.getNumber();
        this.thumbnailUri = contactModel.getThumbnailUri();
        this.dataState = contactModel.getDataState();
        this.inApp = contactModel.getInApp();
    }

    public ContactModel(String id, @NonNull String name, String thumbnailUri, String phoneNumber) {
        this.id = id;
        this.firstName = name;
        this.thumbnailUri = thumbnailUri;
        this.number = phoneNumber;
    }

    public ContactModel(String id, @NonNull String firstName, @NonNull String lastName,
                        String thumbnailUri, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.thumbnailUri = thumbnailUri;
        this.number = phoneNumber;
    }


    public Boolean getInApp() {
        return inApp;
    }

    public void setInApp(Boolean inApp) {
        this.inApp = inApp;
    }

    public String getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getThumbnailUri() {
        return thumbnailUri;
    }

    public void setThumbnailUri(String thumbnailUri) {
        this.thumbnailUri = thumbnailUri;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setNumber(String number) {
        this.number = number;
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

    public DataStates getDataState() {
        return dataState;
    }

    public void setDataState(DataStates dataState) {
        this.dataState = dataState;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ContactModel) {
            ContactModel contact = (ContactModel) obj;
            return this.number.equals(contact.getNumber());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return number.hashCode();
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
        dest.writeString(this.number);
        dest.writeString(this.thumbnailUri);
        dest.writeValue(this.status);
        dest.writeInt(this.dataState == null ? -1 : this.dataState.ordinal());
        dest.writeValue(this.inApp);
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.number = in.readString();
        this.thumbnailUri = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpDataState = in.readInt();
        this.dataState = tmpDataState == -1 ? null : DataStates.values()[tmpDataState];
        this.inApp = (Boolean) in.readValue(Boolean.class.getClassLoader());
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
