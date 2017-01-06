package com.krestone.savealife.presentation.presenters.entry;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.presenters.Presenter;

public interface RegistrationNumberPresenter<T> extends Presenter<T> {

    interface RegistrationNumberView {

        void onRegistrationNumberSent();

        void onAlreadyInUse(SomeoneProfileEntity profileEntity);

        void onRegistrationNumberError(String error);
    }

    void sendRegistrationNumber(String number);

    void progressDialogCancel();
}
