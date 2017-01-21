package com.krestone.savealife.data.di;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.data.sqlite.EmergencyContactsTable;
import com.krestone.savealife.data.sqlite.MessagesTable;
import com.krestone.savealife.data.sqlite.SaveAlifeDatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {RepositoriesModule.class, MiscellaneousModule.class, MappersModule.class})
public class DataModule {

    @Provides
    @Singleton
    SharedPreferences provideSharedPrefs(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    SaveAlifeDatabaseHelper provideDatabaseHelper(Context context) {
        return SaveAlifeDatabaseHelper.getInstance(context);
    }

    @Provides
    @Singleton
    EmergencyContactsTable provideEmergencyContactsTable(SaveAlifeDatabaseHelper helper) {
        return new EmergencyContactsTable(helper);
    }

    @Provides
    @Singleton
    MessagesTable provideMessagesTable(SaveAlifeDatabaseHelper helper) {
        return new MessagesTable(helper);
    }
}
