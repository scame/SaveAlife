package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public interface EmergencyPresenter<T> extends Presenter<T> {

    interface EmergencyView {

        void displayEmergencyList(List<ContactModel> contacts);
    }

    void requestEmergencyContacts();
}
