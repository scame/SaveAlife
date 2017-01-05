package com.krestone.savealife.presentation.presenters.entry;


import com.krestone.savealife.presentation.models.UserModel;
import com.krestone.savealife.presentation.presenters.Presenter;

public interface SignInPresenter<T> extends Presenter<T> {

    interface SignInView {

        void displayUserInfo(UserModel userModel);

        void onSignInSuccess();

        void onSignInErr(String error);
    }

    void requestUserInfo();

    void requestSignIn(String password);
}
