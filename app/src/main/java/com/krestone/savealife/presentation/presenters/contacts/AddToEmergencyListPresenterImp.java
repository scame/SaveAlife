package com.krestone.savealife.presentation.presenters.contacts;


import android.util.Log;

import com.krestone.savealife.domain.usecases.contacts.AddToEmergencyListUseCase;
import com.krestone.savealife.domain.usecases.contacts.GetContactsNotInEmergencyList;
import com.krestone.savealife.presentation.models.ContactModel;

public class AddToEmergencyListPresenterImp<T extends AddToEmergencyListPresenter.AddToEmergencyListView> implements AddToEmergencyListPresenter<T> {

    private T view;

    private GetContactsNotInEmergencyList getContactsNotInEmergencyList;

    private AddToEmergencyListUseCase addToEmergencyListUseCase;

    public AddToEmergencyListPresenterImp(GetContactsNotInEmergencyList useCase,
                                          AddToEmergencyListUseCase addToEmergencyListUseCase) {
        this.getContactsNotInEmergencyList = useCase;
        this.addToEmergencyListUseCase = addToEmergencyListUseCase;
    }

    @Override
    public void requestContacts() {
        getContactsNotInEmergencyList.executeSingle(contactModels -> {
            if (view != null) view.displayContacts(contactModels);
        }, throwable -> Log.i("onxAddToEmrgListErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void addToEmergencyList(ContactModel contactModel) {
        addToEmergencyListUseCase.setContactModel(contactModel);
        addToEmergencyListUseCase.executeCompletable(
                () -> Log.i("onxAddToEmrg", "success"),
                throwable -> Log.i("onxAddToEmrgErr", throwable.toString())
        );
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        this.view = null;
        addToEmergencyListUseCase.unsubscribe();
        getContactsNotInEmergencyList.unsubscribe();
    }
}
