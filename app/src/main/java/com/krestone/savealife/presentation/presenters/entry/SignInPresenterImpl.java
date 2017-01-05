package com.krestone.savealife.presentation.presenters.entry;


import android.util.Log;

import com.krestone.savealife.domain.usecases.entry.GetLastLoggedInUserInfoUseCase;

public class SignInPresenterImpl<T extends SignInPresenter.SignInView> implements SignInPresenter<T> {

    private T view;

    private GetLastLoggedInUserInfoUseCase getLastLoggedInUserInfoUseCase;

    public SignInPresenterImpl(GetLastLoggedInUserInfoUseCase getLastLoggedInUserInfoUseCase) {
        this.getLastLoggedInUserInfoUseCase = getLastLoggedInUserInfoUseCase;
    }

    @Override
    public void requestUserInfo() {
        getLastLoggedInUserInfoUseCase.executeSingle(userModel -> {
            if (view != null) view.displayUserInfo(userModel);
        }, throwable -> Log.i("onxUserInfoErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void requestSignIn(String password) {

    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        getLastLoggedInUserInfoUseCase.unsubscribe();
    }
}
