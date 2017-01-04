package com.krestone.savealife.presentation.presenters;


public interface DrawerActivityPresenter<T> extends Presenter<T> {

    interface DrawerView {

        void startEntryProcess();

        void goNormal();
    }

    void checkLoginStatus();
}
