package com.krestone.savealife.presentation.presenters.contacts;


import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.presentation.presenters.Presenter;

import java.util.List;

public interface EmergencyPresenter<T> extends Presenter<T> {

    interface EmergencyView {

        void displayEmergencyList(List<ContactItem> contacts);
    }

    void requestEmergencyContacts();
}
