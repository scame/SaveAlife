package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.GetAllContactsUseCase;
import com.krestone.savealife.domain.usecases.UpdateEmergencyContactsUseCase;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsPresenterImp<T extends ContactsPresenter.ContactsView> implements ContactsPresenter<T> {

    private GetAllContactsUseCase getAllContactsUseCase;

    private UpdateEmergencyContactsUseCase updateEmergencyContactsUseCase;

    private final List<ContactModel> cachedContacts = new ArrayList<>();

    private T view;

    public ContactsPresenterImp(GetAllContactsUseCase getAllContactsUseCase,
                                UpdateEmergencyContactsUseCase updateEmergencyContactsUseCase) {
        this.getAllContactsUseCase = getAllContactsUseCase;
        this.updateEmergencyContactsUseCase = updateEmergencyContactsUseCase;
    }

    @Override
    public void requestContacts() {
        getAllContactsUseCase.executeSingle(contactModels -> {
            if (view != null) {
                cacheContacts(contactModels);
                view.displayContacts(contactModels);
            }
        }, throwable -> Log.i("onxContactsErr", throwable.getLocalizedMessage()));
    }

    private void cacheContacts(List<ContactModel> contacts) {
        cachedContacts.clear();
        for (ContactModel contact : contacts) {
            cachedContacts.add(new ContactModel(contact));
        }
    }

    @Override
    public void saveUpdatedContacts(List<ContactModel> contacts) {
        updateEmergencyContactsUseCase.setContacts(contacts);
        updateEmergencyContactsUseCase.executeCompletable(
                () -> Log.i("onxUpdateCompleted", "ok"),
                throwable -> Log.i("onxUpdateErr", throwable.toString())
        );
    }

    @Override
    public void compareWithOldModel(List<ContactModel> contacts) {
        if (view != null) {
             view.onDoneComparison(cachedContacts.equals(contacts));
        }
    }

    @Override
    public void cancelChanges() {
        if (cachedContacts.size() != 0 && view != null) {
            view.redrawList(cachedContacts);
        }
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        getAllContactsUseCase.unsubscribe();
        updateEmergencyContactsUseCase.unsubscribe();
        view = null;
    }
}
