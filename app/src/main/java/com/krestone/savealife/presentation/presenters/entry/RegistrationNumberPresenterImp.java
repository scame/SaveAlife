package com.krestone.savealife.presentation.presenters.entry;


import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.entry.RegistrationNumberUseCase;
import com.krestone.savealife.util.ConnectivityUtil;

public class RegistrationNumberPresenterImp<T extends RegistrationNumberPresenter.RegistrationNumberView>
        implements RegistrationNumberPresenter<T> {

    private RegistrationNumberUseCase registrationNumberUseCase;

    private T view;

    public RegistrationNumberPresenterImp(RegistrationNumberUseCase registrationNumberUseCase) {
        this.registrationNumberUseCase = registrationNumberUseCase;
    }

    @Override
    public void sendRegistrationNumber(String number) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            registrationNumberUseCase.setPhoneNumber(number);
            sendRegistrationNumber();
        } else if (view != null) {
            view.onRegistrationNumberError(
                    SaveAlifeApplication.application.getString(R.string.internet_connection_check), false);
        }
    }

    private void sendRegistrationNumber() {
        registrationNumberUseCase.executeSingle(responseBody -> {
            if (view != null) {
                view.onRegistrationNumberSent();
            }
        }, throwable -> {
            Log.i("onxRegistrNumberErr", throwable.getLocalizedMessage());
            if (view != null) {
                // TODO: check that the error corresponds to 'already in use' error
                view.onRegistrationNumberError(throwable.getMessage(), true);
            }
        });
    }

    @Override
    public void progressDialogCancel() {
        registrationNumberUseCase.unsubscribe();
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
