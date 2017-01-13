package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sqlite.EmergencyContactsTable;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;

// TODO: use synchronous approach, get rid of ContactItem (ContactModel should incorporate all fields)
public class EmergencyContactsSync extends AbstractSync {

    private EmergencyContactsTable emergencyTable;

    private ContactsRepository contactsRepository;

    private Context context;

    public EmergencyContactsSync(Context context, EmergencyContactsTable emergencyTable,
                                 ContactsRepository contactsRepository) {
        super(context);
        this.emergencyTable = emergencyTable;
        this.contactsRepository = contactsRepository;
        this.context = context;
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.CONTACTS;
    }

    @Override
    protected Completable post() {
        List<ContactModel> modifiedContacts = emergencyTable.getEmergencyContacts(true);
        contactsRepository.addToEmergencyList(modifiedContacts).await();
        emergencyTable.markAsNotModified(modifiedContacts);

        return Completable.complete();
    }

    @Override
    protected Completable get() {
        ContactsHolder contactsHolder = contactsRepository.getEmergencyContacts().toBlocking().value();
        emergencyTable.deleteAllContacts();
        //emergencyTable.addContacts(contactsHolder.getContacts());

        return Completable.complete();
    }
}
