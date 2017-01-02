package com.krestone.savealife.presentation.presenters.entry;


import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.entry.VerificationUseCase;
import com.krestone.savealife.util.ConnectivityUtil;

public class VerificationPresenterImpl<T extends VerificationPresenter.VerificationView> implements VerificationPresenter<T> {

    private VerificationUseCase verificationUseCase;

    private T view;

    public VerificationPresenterImpl(VerificationUseCase verificationUseCase) {
        this.verificationUseCase = verificationUseCase;
    }

    @Override
    public void verify(String phoneNumber, String verifCode) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            verificationUseCase.setData(phoneNumber, verifCode);
            verify();
        } else if (view != null) {
            view.onVerificationErr(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void verify() {
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
    public void progressDialogCancel() {
        verificationUseCase.unsubscribe();
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
