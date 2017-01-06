package com.krestone.savealife.presentation.presenters.entry;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.presentation.presenters.Presenter;

public interface SignInPresenter<T> extends Presenter<T> {

    interface SignInView {

        void onSignInSuccess();

        void onSignInErr(String error);
    }

    void requestSignIn(String password, SomeoneProfileEntity profileEntity);
}
