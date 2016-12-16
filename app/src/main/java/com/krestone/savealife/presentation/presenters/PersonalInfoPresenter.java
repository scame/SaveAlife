package com.krestone.savealife.presentation.presenters;



public interface PersonalInfoPresenter<T> extends Presenter<T> {

    interface PersonalInfoView {

        void onPersonalInfoSent();

        void onPersonalInfoErr(String error);
    }

    void sendPersonalInfo(String firstName, String lastName,
                          String password, String phoneNumber,
                          String medicalQualification, String verifCode);
}
