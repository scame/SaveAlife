package com.krestone.savealife.data.entities.responses;


import java.util.ArrayList;
import java.util.List;

public class ContactsStatusEntity {

    private List<ContactItem> contacts;

    public ContactsStatusEntity() {
        contacts = new ArrayList<>();
    }

    public void setContacts(List<ContactItem> contacts) {
        this.contacts = contacts;
    }

    public List<ContactItem> getContacts() {
        return contacts;
    }
}
