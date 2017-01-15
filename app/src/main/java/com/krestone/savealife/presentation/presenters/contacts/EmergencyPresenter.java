package com.krestone.savealife.presentation.presenters.contacts;


import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.Presenter;

import java.util.List;

public interface EmergencyPresenter<T> extends Presenter<T> {

    interface EmergencyView {

        void displayEmergencyList(List<ContactModel> contacts);
    }

    void requestEmergencyContacts();
}
