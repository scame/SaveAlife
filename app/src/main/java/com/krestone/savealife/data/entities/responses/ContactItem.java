package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

public class ContactItem implements Parcelable {

    private String firstName;

    private String lastName;

    private String number;

    private Integer status;

    private Boolean isInApp;

    public void setNumber(String number) {
        this.number = number;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getStatus() {
        return status;
    }

    public Boolean getInApp() {
        return isInApp;
    }

    public void setInApp(Boolean inApp) {
        isInApp = inApp;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.number);
        dest.writeValue(this.status);
        dest.writeValue(this.isInApp);
    }

    public ContactItem() {
    }

    protected ContactItem(Parcel in) {
        this.firstName = in.readString();
        this.lastName = in.readString();
        this.number = in.readString();
        this.status = (Integer) in.readValue(Integer.class.getClassLoader());
        this.isInApp = (Boolean) in.readValue(Boolean.class.getClassLoader());
    }

    public static final Creator<ContactItem> CREATOR = new Creator<ContactItem>() {
        @Override
        public ContactItem createFromParcel(Parcel source) {
            return new ContactItem(source);
        }

        @Override
        public ContactItem[] newArray(int size) {
            return new ContactItem[size];
        }
    };
}
