package com.krestone.savealife.presentation.presenters;


public interface SettingsPresenter<T> extends Presenter<T> {

    interface SettingsView {

        void onError(String error);
    }

    void changeLocationUpdates(boolean isEnabled);

    void changeMessagesState(boolean isEnabled);
}
