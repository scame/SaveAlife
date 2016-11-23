package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.GetAllContactsUseCase;

public class ContactsPresenterImp<T extends ContactsPresenter.ContactsView> implements ContactsPresenter<T> {

    private GetAllContactsUseCase getAllContactsUseCase;

    private T view;

    public ContactsPresenterImp(GetAllContactsUseCase getAllContactsUseCase) {
        this.getAllContactsUseCase = getAllContactsUseCase;
    }

    @Override
    public void requestContacts() {
        getAllContactsUseCase.executeSingle(contactModels -> {
            if (view != null) {
                view.displayContacts(contactModels);
            }
        }, throwable -> Log.i("onxContactsErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        getAllContactsUseCase.unsubscribe();
        view = null;
    }
}
