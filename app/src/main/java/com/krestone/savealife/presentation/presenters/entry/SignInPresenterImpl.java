package com.krestone.savealife.presentation.presenters.entry;


import android.util.Log;

import com.krestone.savealife.domain.usecases.entry.GetLastLoggedInUserInfoUseCase;
import com.krestone.savealife.domain.usecases.entry.SignInUseCase;

public class SignInPresenterImpl<T extends SignInPresenter.SignInView> implements SignInPresenter<T> {

    private T view;

    private GetLastLoggedInUserInfoUseCase getLastLoggedInUserInfoUseCase;

    private SignInUseCase signInUseCase;

    public SignInPresenterImpl(GetLastLoggedInUserInfoUseCase getLastLoggedInUserInfoUseCase,
                               SignInUseCase signInUseCase) {
        this.getLastLoggedInUserInfoUseCase = getLastLoggedInUserInfoUseCase;
        this.signInUseCase = signInUseCase;
    }

    @Override
    public void requestUserInfo() {
        getLastLoggedInUserInfoUseCase.executeSingle(userModel -> {
            if (view != null) view.displayUserInfo(userModel);
        }, throwable -> Log.i("onxUserInfoErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void requestSignIn(String password) {
        signInUseCase.setPassword(password);
        signInUseCase.executeSingle(passwordMatches -> {
            if (view != null) {
                if (passwordMatches) {
                    view.onSignInSuccess();
                } else {
                    view.onSignInErr("Passwords don't match");
                }
            }
        }, throwable -> Log.i("onxSignInErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        signInUseCase.unsubscribe();
        getLastLoggedInUserInfoUseCase.unsubscribe();
    }
}
