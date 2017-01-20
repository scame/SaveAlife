package com.krestone.savealife.presentation.presenters;


public interface DashboardPresenter<T> extends Presenter<T> {

    interface DashboardView {

        void onStartSosCompleted();

        void onStopSosCompleted();

        void onError(String error);
    }

    void startSos();

    void stopSos();
}
