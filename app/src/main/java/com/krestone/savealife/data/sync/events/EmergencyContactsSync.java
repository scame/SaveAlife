package com.krestone.savealife.data.sync.events;


import android.content.Context;
import android.util.Log;

import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sqlite.EmergencyContactsTable;
import com.krestone.savealife.data.sync.AbstractSync;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

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
    protected void post() {
        List<ContactModel> modifiedContacts = emergencyTable.getEmergencyContacts(true);
        contactsRepository.addToEmergencyList(modifiedContacts)
                .subscribe(() -> {
                            emergencyTable.markAsNotModified(modifiedContacts);
                            get();
                        }, throwable -> Log.i("onxPostErr", throwable.getLocalizedMessage())
                );

    }

    @Override
    protected void get() {
        contactsRepository.getEmergencyContacts()
                .subscribe(contactsHolder -> {
                    emergencyTable.deleteAllContacts();
                    //emergencyTable.addContacts(contactsHolder.getContacts());
                }, throwable -> Log.i("onxGetErr", throwable.getLocalizedMessage()));
    }
}
