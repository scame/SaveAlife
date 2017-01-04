package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.entry.SignOutUseCase;

public class MyProfilePresenterImpl<T extends MyProfilePresenter.MyProfileView> implements MyProfilePresenter<T> {

    private T view;

    private SignOutUseCase signOutUseCase;

    public MyProfilePresenterImpl(SignOutUseCase signOutUseCase) {
        this.signOutUseCase = signOutUseCase;
    }

    @Override
    public void signOut() {
        signOutUseCase.executeCompletable(() -> {
            if (view != null) {
                view.onSignOut();
            }
        }, throwable -> Log.i("onxSignOutErr", throwable.toString()));
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        signOutUseCase.unsubscribe();
    }
}
