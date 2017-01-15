package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sync.events.SyncType;
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
        List<ContactModel> modifiedContacts = contactsRepository
                .getEmergencyContactsLocal(true)
                .toBlocking().value()
                .getContacts();

        contactsRepository.addToEmergencyList(modifiedContacts).await();
        contactsRepository.markAsNotModified(modifiedContacts).await();

        return Completable.complete();
    }

    @Override
    protected Completable get() {
        List<ContactModel> freshContacts = contactsRepository
                .getEmergencyContacts()
                .toBlocking().value()
                .getContacts();

        contactsRepository.cleanLocalContactsList().await();
        contactsRepository.addToEmergencyListLocal(freshContacts).await();

        return Completable.complete();
    }
}
