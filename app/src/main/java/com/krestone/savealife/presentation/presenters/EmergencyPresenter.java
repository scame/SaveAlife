package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.entities.responses.ContactItem;

import java.util.List;

public interface EmergencyPresenter<T> extends Presenter<T> {

    interface EmergencyView {

        void displayEmergencyList(List<ContactItem> contacts);
    }

    void requestEmergencyContacts();
}
