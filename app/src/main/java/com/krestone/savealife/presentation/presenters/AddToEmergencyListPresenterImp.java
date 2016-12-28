package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.domain.usecases.contacts.AddToEmergencyListUseCase;
import com.krestone.savealife.domain.usecases.contacts.GetPossibleEmergencyContactsUseCase;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.Collections;

public class AddToEmergencyListPresenterImp<T extends AddToEmergencyListPresenter.AddToEmergencyListView> implements AddToEmergencyListPresenter<T> {

    private T view;

    private GetPossibleEmergencyContactsUseCase getPossibleEmergencyContactsUseCase;

    private AddToEmergencyListUseCase addToEmergencyListUseCase;

    public AddToEmergencyListPresenterImp(GetPossibleEmergencyContactsUseCase useCase,
                                          AddToEmergencyListUseCase addToEmergencyListUseCase) {
        this.getPossibleEmergencyContactsUseCase = useCase;
        this.addToEmergencyListUseCase = addToEmergencyListUseCase;
    }

    @Override
    public void requestContacts() {
        getPossibleEmergencyContactsUseCase.executeSingle(contactModels -> {
            if (view != null) view.displayContacts(contactModels);
        }, throwable -> {
            Log.i("onxAddToEmrgListErr", throwable.getLocalizedMessage());
        });
    }

    @Override
    public void addToEmergencyList(ContactModel contactModel) {
        addToEmergencyListUseCase.setContactsNumbersHolder(
                new ContactsNumbersHolder(Collections.singletonList(contactModel.getMobileNumber()))
        );
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
        addToEmergencyListUseCase = null;
        getPossibleEmergencyContactsUseCase = null;
    }
}
