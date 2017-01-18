package com.krestone.savealife.presentation.presenters.contacts;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.presenters.Presenter;

public interface ContactProfilePresenter<T> extends Presenter<T> {

    interface ContactProfileView {

        void displayProfileInfo(SomeoneProfileEntity profileInfo);

        void onError(String error);
    }

    void requestProfileInfo(String phoneNumber);

    void removeContact(String phoneNumber);

    void progressDialogCancel();
}
