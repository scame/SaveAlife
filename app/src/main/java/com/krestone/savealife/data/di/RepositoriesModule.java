package com.krestone.savealife.data.di;


import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.repository.ContactsRepositoryImp;
import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.data.repository.LocationRepositoryImp;
import com.krestone.savealife.data.sqlite.SaveAlifeDatabaseHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class RepositoriesModule {

    @Provides
    @Singleton
    LocationRepository provideLocationRepository(Context context, ReactiveLocationProvider locationProvider,
                                                 LocationRequest locationRequest) {
        return new LocationRepositoryImp(context, locationProvider, locationRequest);
    }

    @Provides
    @Singleton
    ContactsRepository provideContactsRepository(Context context, SaveAlifeDatabaseHelper databaseHelper) {
        return new ContactsRepositoryImp(context, databaseHelper);
    }
}
