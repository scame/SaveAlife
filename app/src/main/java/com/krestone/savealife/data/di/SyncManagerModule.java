package com.krestone.savealife.data.di;

import android.content.Context;

import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sqlite.EmergencyContactsTable;
import com.krestone.savealife.data.sync.SyncManager;
import com.krestone.savealife.data.sync.EmergencyContactsSync;

import dagger.Module;
import dagger.Provides;

@Module
public class SyncManagerModule {

    @Provides
    @PerService
    SyncManager provideSyncManager(EmergencyContactsSync emergencyContactsSync) {
        return new SyncManager(emergencyContactsSync);
    }

    @Provides
    @PerService
    EmergencyContactsSync provideEmergencyContactsSync(Context context, EmergencyContactsTable emergencyContactsTable,
                                                       ContactsRepository contactsRepository) {
        return new EmergencyContactsSync(context, emergencyContactsTable, contactsRepository);
    }
}
