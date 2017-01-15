package com.krestone.savealife.data.entities.responses;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsHolder {

    private List<ContactModel> contacts;

    public ContactsHolder() {
        contacts = new ArrayList<>();
    }

    public ContactsHolder(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }
}
