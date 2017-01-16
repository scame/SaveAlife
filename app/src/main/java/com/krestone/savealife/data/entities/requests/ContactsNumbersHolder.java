package com.krestone.savealife.data.entities.requests;


import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class ContactsNumbersHolder {

    private List<String> numbers;

    public ContactsNumbersHolder() {
        numbers = new ArrayList<>();
    }

    public ContactsNumbersHolder(List<String> numbers) {
        this.numbers = numbers;
    }

    public static ContactsNumbersHolder fromContacts(List<ContactModel> contacts) {
        List<String> numbers = new ArrayList<>();
        for (ContactModel contact : contacts) {
            numbers.add(contact.getPhoneNumber());
        }
        return new ContactsNumbersHolder(numbers);
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }

    public List<String> getNumbers() {
        return numbers;
    }
}
