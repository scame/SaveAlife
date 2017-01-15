package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;
import rx.Single;

public interface ContactsRepository {

    Single<List<ContactModel>> getContactsNotInEmergencyList();

    Single<List<ContactModel>> getContactsNotInEmergencyListLocal();

    Single<ContactsHolder> getContactsInApp();

    Completable deleteFromEmergencyList(ContactsNumbersHolder contactsNumbersHolder);

    Completable deleteFromEmergencyListLocal(List<ContactModel> contacts);

    Completable cleanLocalContactsList();

    Completable addToEmergencyList(List<ContactModel> contactModels);

    Completable addToEmergencyListLocal(List<ContactModel> contactModels);

    Single<ContactsHolder> getEmergencyContacts();

    Single<ContactsHolder> getEmergencyContactsLocal(boolean onlyModified);

    Completable markAsNotModified(List<ContactModel> contacts);
}
