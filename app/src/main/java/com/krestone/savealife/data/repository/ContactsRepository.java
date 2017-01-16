package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.sync.states.DataStates;
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

    Completable addOrUpdateEmergencyContacts(List<ContactModel> contactModels);

    Completable addToEmergencyList(List<ContactModel> contactModels);

    Single<ContactsHolder> getEmergencyContacts();

    Single<ContactsHolder> getEmergencyContactsLocal();

    Single<ContactsHolder> getEmergencyContactsLocalByState(DataStates dataState);

    Completable updateDataState(List<ContactModel> contacts, DataStates dataState);
}
