package com.krestone.savealife.data.entities.responses;


import java.util.ArrayList;
import java.util.List;

public class ContactsHolder {

    private List<ContactItem> contacts;

    public ContactsHolder() {
        contacts = new ArrayList<>();
    }

    public ContactsHolder(List<ContactItem> contacts) {
        this.contacts = contacts;
    }

    public void setContacts(List<ContactItem> contacts) {
        this.contacts = contacts;
    }

    public List<ContactItem> getContacts() {
        return contacts;
    }
}
