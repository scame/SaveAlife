package com.krestone.savealife.presentation.presenters.entry;


import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.domain.usecases.entry.SignInUseCase;
import com.krestone.savealife.util.ConnectivityUtil;

public class SignInPresenterImpl<T extends SignInPresenter.SignInView> implements SignInPresenter<T> {

    private T view;

    private SignInUseCase signInUseCase;

    public SignInPresenterImpl(SignInUseCase signInUseCase) {
        this.signInUseCase = signInUseCase;
    }

    @Override
    public void requestSignIn(String password, SomeoneProfileEntity profileEntity) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            signInUseCase.setPassword(password);
            signInUseCase.setProfileEntity(profileEntity);
            requestSignIn();
        } else if (view != null) {
            view.onSignInErr(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void requestSignIn() {
        signInUseCase.executeSingle(passwordMatches -> {
            if (view != null) {
                if (passwordMatches) {
                    view.onSignInSuccess();
                } else {
                    view.onSignInErr(SaveAlifeApplication.application.getString(R.string.dont_match));
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
    }
}
