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

    private Completable handleRemovedContacts() {
        List<ContactModel> removedContacts = getContactsByState(DataStates.REMOVED);

        return contactsRepository
                .deleteFromEmergencyList(ContactsNumbersHolder.fromContacts(removedContacts))
                .andThen(contactsRepository.deleteFromEmergencyListLocal(removedContacts));
    }

    private Completable handleNewContacts() {
        List<ContactModel> newContacts = getContactsByState(DataStates.NEW);

        return contactsRepository
                .addToEmergencyList(newContacts)
                .andThen(contactsRepository.updateDataState(newContacts, DataStates.UP_TO_DATE));
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

        return contactsRepository
                .cleanLocalContactsList()
                .andThen(contactsRepository.addOrUpdateEmergencyContacts(freshContacts));
    }
}
