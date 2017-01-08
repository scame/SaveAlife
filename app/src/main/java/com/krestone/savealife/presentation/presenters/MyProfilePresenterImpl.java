package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.entry.SignOutUseCase;
import com.krestone.savealife.domain.usecases.profiles.GetMyProfileInfoUseCase;

public class MyProfilePresenterImpl<T extends MyProfilePresenter.MyProfileView> implements MyProfilePresenter<T> {

    private T view;

    private GetMyProfileInfoUseCase getMyProfileInfoUseCase;

    private SignOutUseCase signOutUseCase;

    public MyProfilePresenterImpl(GetMyProfileInfoUseCase getMyProfileInfoUseCase,
                                  SignOutUseCase signOutUseCase) {
        this.getMyProfileInfoUseCase = getMyProfileInfoUseCase;
        this.signOutUseCase = signOutUseCase;
    }

    @Override
    public void requestMyProfileInfo() {
        getMyProfileInfoUseCase.executeSingle(myProfileInfoEntity -> {
            if (view != null) {
                view.displayMyProfileInfo(myProfileInfoEntity);
            }
        }, throwable -> Log.i("onxMyInfoErr", throwable.getLocalizedMessage()));
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
        getMyProfileInfoUseCase.unsubscribe();
    }
}
