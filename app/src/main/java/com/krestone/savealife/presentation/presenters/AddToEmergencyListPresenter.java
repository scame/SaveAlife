package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public interface AddToEmergencyListPresenter<T> extends Presenter<T> {

    interface AddToEmergencyListView {

        void displayContacts(List<ContactModel> contacts);
    }

    void requestContacts();

    void addToEmergencyList(ContactModel contactModel);
}
