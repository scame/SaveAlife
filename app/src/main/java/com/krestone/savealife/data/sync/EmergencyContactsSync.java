package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;


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
        return handleRemovedContacts().andThen(handleNewContacts());
    }

    private Completable handleNewContacts() {
        List<ContactModel> newContacts = getContactsByState(DataStates.NEW);

        contactsRepository.addToEmergencyList(newContacts).await();
        contactsRepository.updateDataState(newContacts, DataStates.UP_TO_DATE).await();
        return Completable.complete();
    }

    private Completable handleRemovedContacts() {
        List<ContactModel> removedContacts = getContactsByState(DataStates.REMOVED);

        contactsRepository.deleteFromEmergencyList(ContactsNumbersHolder.fromContacts(removedContacts));
        contactsRepository.updateDataState(removedContacts, DataStates.UP_TO_DATE);
        return Completable.complete();
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

        contactsRepository.cleanLocalContactsList().await();
        contactsRepository.addOrUpdateToEmergencyList(freshContacts).await();

        return Completable.complete();
    }
}
