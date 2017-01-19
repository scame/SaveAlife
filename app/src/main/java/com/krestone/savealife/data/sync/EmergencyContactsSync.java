package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;

// TODO: 1/18/17 rewrite in asynchronous manner
public class EmergencyContactsSync extends AbstractSync {

    private ContactsRepository contactsRepository;

    public EmergencyContactsSync(Context context, ContactsRepository contactsRepository) {
        super(context);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.CONTACTS;
    }

    @Override
    protected Completable post() {
        handleRemovedContacts().andThen(handleNewContacts()).await();
        return Completable.complete();
    }

    private Completable handleRemovedContacts() {
        List<ContactModel> removedContacts = getContactsByState(DataStates.REMOVED);

        Throwable throwable = contactsRepository
                .deleteFromEmergencyList(ContactsNumbersHolder.fromContacts(removedContacts))
                .andThen(contactsRepository.deleteFromEmergencyListLocal(removedContacts)).get();
        return completeWithErrorCheck(throwable);
    }

    private Completable handleNewContacts() {
        List<ContactModel> newContacts = getContactsByState(DataStates.NEW);

        Throwable throwable = contactsRepository
                .addToEmergencyList(newContacts)
                .andThen(contactsRepository.updateDataState(newContacts, DataStates.UP_TO_DATE)).get();
        return completeWithErrorCheck(throwable);
    }

    private List<ContactModel> getContactsByState(DataStates state) {
        return contactsRepository
                .getEmergencyContactsLocalByState(state)
                .toBlocking().value()
                .getContacts();
    }

    @Override
    protected Completable get() {
        List<ContactModel> freshContacts = contactsRepository
                .getEmergencyContacts()
                .toBlocking().value()
                .getContacts();

        Throwable throwable = contactsRepository
                .cleanLocalContactsList()
                .andThen(contactsRepository.addOrUpdateEmergencyContacts(freshContacts)).get();
        return completeWithErrorCheck(throwable);
    }
}
