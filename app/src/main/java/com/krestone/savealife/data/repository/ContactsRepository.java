package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;
import rx.Single;

public interface ContactsRepository {

    Single<List<ContactModel>> getContacts();

    Completable updateEmergencyContacts(List<ContactModel> contacts);

    Single<ContactsNumbersHolder> getContactsInApp();

    Completable deleteFromEmergencyList(ContactsNumbersHolder contactsNumbersHolder);

    Completable addToEmergencyList(ContactsNumbersHolder contactsNumbersHolder);

    Single<ContactsNumbersHolder> getEmergencyContacts();
}
