package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.RegistrationNumberUseCase;

public class RegistrationNumberPresenterImp<T extends RegistrationNumberPresenter.RegistrationNumberView>
        implements RegistrationNumberPresenter<T> {

    private RegistrationNumberUseCase registrationNumberUseCase;

    private T view;

    public RegistrationNumberPresenterImp(RegistrationNumberUseCase registrationNumberUseCase) {
        this.registrationNumberUseCase = registrationNumberUseCase;
    }

    @Override
    public void sendRegistrationNumber(String number) {
        registrationNumberUseCase.setPhoneNumber(number);
        registrationNumberUseCase.executeSingle(responseBody -> {
            if (view != null) {
                view.onRegistrationNumberSent();
            }
        }, throwable -> {
            Log.i("onxRegistrNumberErr", throwable.getLocalizedMessage());
            if (view != null) {
                view.onRegistrationNumberError(throwable.getMessage());
            }
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        registrationNumberUseCase.unsubscribe();
    }
}
