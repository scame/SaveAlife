package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class MapContactModelToContactsHolder {

    public ContactsHolder map(List<ContactModel> contactModels) {
        List<ContactItem> contactItems = new ArrayList<>();

        for (ContactModel contactModel : contactModels) {
            ContactItem contactItem = new ContactItem();
            contactItem.setName(contactModel.getName());
            contactItem.setNumber(contactModel.getPhoneNumber());
            //contactItem.setStatus()

            contactItems.add(contactItem);
        }
        return new ContactsHolder(contactItems);
    }
}
