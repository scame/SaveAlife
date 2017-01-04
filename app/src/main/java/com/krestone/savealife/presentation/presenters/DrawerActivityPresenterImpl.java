package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.entry.CheckLoginUseCase;

public class DrawerActivityPresenterImpl<T extends DrawerActivityPresenter.DrawerView> implements DrawerActivityPresenter<T> {

    private T view;

    private CheckLoginUseCase checkLoginUseCase;

    public DrawerActivityPresenterImpl(CheckLoginUseCase checkLoginUseCase) {
        this.checkLoginUseCase = checkLoginUseCase;
    }

    @Override
    public void checkLoginStatus() {
        checkLoginUseCase.executeSingle(isLoggedIn -> {
            if (view != null) {
                if (isLoggedIn) view.goNormal();
                else view.startEntryProcess();
            }
        }, throwable -> Log.i("onxLoginCheckErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        checkLoginUseCase.unsubscribe();
    }
}
