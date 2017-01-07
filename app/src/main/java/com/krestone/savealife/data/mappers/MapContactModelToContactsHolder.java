package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.Collections;

public class MapContactModelToContactsHolder {

    public ContactsHolder map(ContactModel contactModel) {
        ContactItem contactItem = new ContactItem();
        contactItem.setFirstName(contactModel.getName());
        contactItem.setNumber(contactModel.getMobileNumber());
        return new ContactsHolder(Collections.singletonList(contactItem));
    }
}
