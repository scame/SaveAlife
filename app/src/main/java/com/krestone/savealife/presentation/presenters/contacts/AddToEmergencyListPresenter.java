package com.krestone.savealife.presentation.presenters.contacts;


import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.Presenter;

import java.util.List;

public interface AddToEmergencyListPresenter<T> extends Presenter<T> {

    interface AddToEmergencyListView {

        void displayContacts(List<ContactModel> contacts);
    }

    void requestContacts();

    void addToEmergencyList(ContactModel contactModel);
}
