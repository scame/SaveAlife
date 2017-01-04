package com.krestone.savealife.presentation.presenters;



public interface MyProfilePresenter<T> extends Presenter<T> {

    interface MyProfileView {

        void onSignOut();
    }

    void signOut();
}
