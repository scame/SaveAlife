package com.krestone.savealife.data.di;

import android.content.Context;

import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.data.sync.EmergencyContactsSync;
import com.krestone.savealife.data.sync.ProfileSync;
import com.krestone.savealife.data.sync.SyncManager;

import dagger.Module;
import dagger.Provides;

@Module
public class SyncManagerModule {

    @Provides
    @PerService
    SyncManager provideSyncManager(EmergencyContactsSync emergencyContactsSync, ProfileSync profileSync) {
        return new SyncManager(emergencyContactsSync, profileSync);
    }

    @Provides
    @PerService
    EmergencyContactsSync provideEmergencyContactsSync(Context context, ContactsRepository contactsRepository) {
        return new EmergencyContactsSync(context, contactsRepository);
    }

    @Provides
    @PerService
    ProfileSync provideProfileSync(Context context, ProfileRepository profileRepository) {
        return new ProfileSync(context, profileRepository);
    }
}
