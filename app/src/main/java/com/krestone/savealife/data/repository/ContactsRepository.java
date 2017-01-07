package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;
import rx.Single;

public interface ContactsRepository {

    Single<List<ContactModel>> getContactsNotInEmergencyList();

    Single<ContactsHolder> getContactsInApp();

    Completable deleteFromEmergencyList(ContactsNumbersHolder contactsNumbersHolder);

    Completable addToEmergencyList(ContactModel contactModel);

    Single<ContactsHolder> getEmergencyContacts();
}
