package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.VerificationUseCase;

public class VerificationPresenterImpl<T extends VerificationPresenter.VerificationView> implements VerificationPresenter<T> {

    private VerificationUseCase verificationUseCase;

    private T view;

    public VerificationPresenterImpl(VerificationUseCase verificationUseCase) {
        this.verificationUseCase = verificationUseCase;
    }

    @Override
    public void verify(String phoneNumber, String verifCode) {
        verificationUseCase.setData(phoneNumber, verifCode);
        verificationUseCase.executeSingle(responseBody -> {
            if (view != null) {
                view.onVerificationSuccess();
            }
        }, throwable -> {
            Log.i("onxVerifFailed", throwable.getLocalizedMessage());
            if (view != null) {
                view.onVerificationErr(throwable.getLocalizedMessage());
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
        verificationUseCase.unsubscribe();
    }
}
