package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.settings.ChangeLocationUpdatesUseCase;
import com.krestone.savealife.domain.usecases.settings.ChangeMessagesStateUseCase;

public class SettingsPresenterImpl<T extends SettingsPresenter.SettingsView> implements SettingsPresenter<T> {

    private T view;

    private ChangeLocationUpdatesUseCase locationUpdatesUseCase;

    private ChangeMessagesStateUseCase messagesStateUseCase;

    public SettingsPresenterImpl(ChangeLocationUpdatesUseCase locationUpdatesUseCase,
                                 ChangeMessagesStateUseCase messagesStateUseCase) {
        this.locationUpdatesUseCase = locationUpdatesUseCase;
        this.messagesStateUseCase = messagesStateUseCase;
    }

    @Override
    public void changeLocationUpdates(boolean isEnabled) {
        locationUpdatesUseCase.setEnabled(isEnabled);
        locationUpdatesUseCase.executeCompletable(
                () -> Log.i("onxLocationUpdates", "updated"),
                throwable -> {
                    if (view != null) view.onError(throwable.toString());
                });
    }

    @Override
    public void changeMessagesState(boolean isEnabled) {
        locationUpdatesUseCase.setEnabled(isEnabled);
        locationUpdatesUseCase.executeCompletable(
                () -> Log.i("onxLocationUpdates", "updated"),
                throwable -> {
                    if (view != null) view.onError(throwable.toString());
                });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        this.view = null;
        locationUpdatesUseCase.unsubscribe();
        messagesStateUseCase.unsubscribe();
    }
}
