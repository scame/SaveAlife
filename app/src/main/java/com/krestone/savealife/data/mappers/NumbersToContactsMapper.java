package com.krestone.savealife.data.mappers;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.ArrayList;
import java.util.List;

public class NumbersToContactsMapper {

    public List<ContactModel> map(ContactsNumbersHolder numbersHolder, List<ContactModel> contactModels) {
        List<ContactModel> mappedContacts = new ArrayList<>();

        for (String number : numbersHolder.getNumbers()) {
            for (ContactModel contactModel : contactModels) {
                if (number.equals(contactModel.getMobileNumber())) {
                    mappedContacts.add(contactModel);
                    break;
                }
            }
        }
        return mappedContacts;
    }
}
