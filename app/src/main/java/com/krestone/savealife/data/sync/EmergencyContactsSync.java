package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;
import rx.Single;


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
        return getContactsByState(DataStates.REMOVED)
                .doOnEach(notification -> {
                    List<ContactModel> contacts = notification.getValue().getContacts();
                    contactsRepository.deleteFromEmergencyList(ContactsNumbersHolder.fromContacts(contacts));
                    contactsRepository.deleteFromEmergencyListLocal(contacts);
                }).toCompletable();
    }

    private Completable handleNewContacts() {
        return getContactsByState(DataStates.NEW)
                .doOnEach(notification -> {
                    List<ContactModel> contacts = notification.getValue().getContacts();
                    contactsRepository.addToEmergencyList(contacts);
                    contactsRepository.updateDataState(contacts, DataStates.UP_TO_DATE);
                }).toCompletable();
    }

    private Single<ContactsHolder> getContactsByState(DataStates state) {
        return contactsRepository.getEmergencyContactsLocalByState(state);
    }

    @Override
    protected Completable get() {
        return contactsRepository.cleanLocalContactsList()
                .toSingle(() -> contactsRepository.getEmergencyContacts())
                .flatMap(contactsHolderSingle -> contactsHolderSingle)
                .map(ContactsHolder::getContacts)
                .map(contactModels -> contactsRepository.addOrUpdateEmergencyContacts(contactModels))
                .toCompletable();
    }
}
