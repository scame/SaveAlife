package com.krestone.savealife.presentation.presenters;


public interface DashboardPresenter<T> extends Presenter<T> {

    interface DashboardView {

        void onStartSosCompleted();

        void onStopSosCompleted();

        void displayIsDriverState(boolean isDriver);

        void onError(String error);
    }

    void startSos();

    void stopSos();

    void getMyProfileInfo();

    void updateMyProfileInfo(boolean isDriver);
}
