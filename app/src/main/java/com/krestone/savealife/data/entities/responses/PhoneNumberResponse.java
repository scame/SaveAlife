package com.krestone.savealife.data.entities.responses;


public class PhoneNumberResponse {

    private boolean isAlreadyInUse;

    private SomeoneProfileEntity profileEntity;

    public boolean isAlreadyInUse() {
        return isAlreadyInUse;
    }

    public void setAlreadyInUse(boolean alreadyInUse) {
        isAlreadyInUse = alreadyInUse;
    }

    public SomeoneProfileEntity getProfileEntity() {
        return profileEntity;
    }

    public void setProfileEntity(SomeoneProfileEntity profileEntity) {
        this.profileEntity = profileEntity;
    }
}
