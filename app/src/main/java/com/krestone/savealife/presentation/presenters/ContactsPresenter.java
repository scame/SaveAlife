package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public interface ContactsPresenter<T> extends Presenter<T> {

    interface ContactsView {

        void displayContacts(List<ContactModel> contacts);
    }

    void requestContacts();
}
