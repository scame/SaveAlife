package com.krestone.savealife.presentation.presenters.entry;


import com.krestone.savealife.presentation.presenters.Presenter;

public interface VerificationPresenter<T> extends Presenter<T> {

    interface VerificationView {

        void onVerificationSuccess();

        void onVerificationErr(String error);
    }

    void verify(String phoneNumber, String verifCode);

    void progressDialogCancel();
}
