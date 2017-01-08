package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;

public interface MyProfilePresenter<T> extends Presenter<T> {

    interface MyProfileView {

        void onSignOut();

        void displayMyProfileInfo(MyProfileInfoEntity profileInfo);
    }

    void requestMyProfileInfo();

    void signOut();
}
