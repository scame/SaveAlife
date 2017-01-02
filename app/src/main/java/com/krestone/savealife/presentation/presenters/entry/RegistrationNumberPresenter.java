package com.krestone.savealife.presentation.presenters.entry;


import com.krestone.savealife.presentation.presenters.Presenter;

public interface RegistrationNumberPresenter<T> extends Presenter<T> {

    interface RegistrationNumberView {

        void onRegistrationNumberSent();

        void onRegistrationNumberError(String error);
    }

    void sendRegistrationNumber(String number);

    void progressDialogCancel();
}
