package com.krestone.savealife.presentation.presenters;



public interface RegistrationNumberPresenter<T> extends Presenter<T> {

    interface RegistrationNumberView {

        void onRegistrationNumberSent();

        void onRegistrationNumberError(String error);
    }

    void sendRegistrationNumber(String number);
}
