package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class NotInEmergencyListFilter {

    public List<ContactModel> filter(List<ContactModel> queriedContacts, ContactsHolder emergencyContacts) {
        List<ContactModel> contactsCopy = copyContactsList(queriedContacts);

        for (ContactModel emergencyContact : emergencyContacts.getContacts()) {
            for (ContactModel localContact : queriedContacts) {
                if (emergencyContact.getNumber().equals(localContact.getNumber())) {
                    contactsCopy.remove(localContact);
                }
            }
        }
        return contactsCopy;
    }

    private List<ContactModel> copyContactsList(List<ContactModel> contacts) {
        List<ContactModel> contactModels = new ArrayList<>();

        for (ContactModel contactModel : contacts) {
            contactModels.add(contactModel);
        }
        return contactModels;
    }
}
