package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsStatusEntity;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;
import rx.Single;

public interface ContactsRepository {

    Single<List<ContactModel>> getContacts();

    Single<List<ContactModel>> getEmergencyContacts();

    Completable updateEmergencyContacts(List<ContactModel> contacts);

    Single<ContactsStatusEntity> getContactsStatus();

    Completable deleteContact(ContactsNumbersHolder contactsNumbersHolder);
}
