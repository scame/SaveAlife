package com.krestone.savealife.presentation.models;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.data.sync.states.InAppContact;

public class ContactModel implements Parcelable {

    private String id;

    @NonNull // concatenated name (first + last names)
    private String firstName;

    // used only for current user profile (otherwise - null)
    private String lastName;

    private String phoneNumber;

    private String thumbnailUri;

    private Integer status;

    private InAppContact inAppState;

    private DataStates dataState;

    public ContactModel() { }

    public ContactModel(ContactModel contactModel) {
        this.id = contactModel.getId();
        this.firstName = contactModel.getFirstName();
        this.phoneNumber = contactModel.getPhoneNumber();
        this.thumbnailUri = contactModel.getThumbnailUri();
        this.inAppState = contactModel.getInAppState();
        this.dataState = contactModel.getDataState();
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

    public DataStates getDataState() {
        return dataState;
    }

    public void setDataState(DataStates dataState) {
        this.dataState = dataState;
    }

    public InAppContact getInAppState() {
        return inAppState;
    }

    public void setInAppState(InAppContact inAppState) {
        this.inAppState = inAppState;
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
        dest.writeInt(this.inAppState == null ? -1 : this.inAppState.ordinal());
        dest.writeInt(this.dataState == null ? -1 : this.dataState.ordinal());
    }

    protected ContactModel(Parcel in) {
        this.id = in.readString();
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.phoneNumber = in.readString();
        this.thumbnailUri = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        int tmpInAppState = in.readInt();
        this.inAppState = tmpInAppState == -1 ? null : InAppContact.values()[tmpInAppState];
        int tmpDataState = in.readInt();
        this.dataState = tmpDataState == -1 ? null : DataStates.values()[tmpDataState];
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
