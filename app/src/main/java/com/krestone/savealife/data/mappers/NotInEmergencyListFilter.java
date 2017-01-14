package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class NotInEmergencyListFilter {

    public List<ContactModel> filter(List<ContactModel> queriedContacts, ContactsHolder emergencyContacts) {

        for (ContactModel queriedContact : queriedContacts) {
            for (ContactItem emergencyContact : emergencyContacts.getContacts()) {
                if (queriedContact.getPhoneNumber().equals(emergencyContact.getNumber())) {
                    queriedContacts.remove(queriedContact);
                    break;
                }
            }
        }
        return queriedContacts;
    }
}
