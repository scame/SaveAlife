package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.DefaultSubscriber;
import com.krestone.savealife.domain.usecases.GetAllContactsUseCase;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class ContactsPresenterImp<T extends ContactsPresenter.ContactsView> implements ContactsPresenter<T> {

    private GetAllContactsUseCase getAllContactsUseCase;

    private T view;

    public ContactsPresenterImp(GetAllContactsUseCase getAllContactsUseCase) {
        this.getAllContactsUseCase = getAllContactsUseCase;
    }

    @Override
    public void requestContacts() {
        getAllContactsUseCase.execute(new ContactsSubscriber());
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

    private final class ContactsSubscriber extends DefaultSubscriber<List<ContactModel>> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxContactsErr", e.getLocalizedMessage());
        }

        @Override
        public void onNext(List<ContactModel> contactModels) {
            super.onNext(contactModels);
            if (view != null) {
                view.displayContacts(contactModels);
            }
        }
    }
}
