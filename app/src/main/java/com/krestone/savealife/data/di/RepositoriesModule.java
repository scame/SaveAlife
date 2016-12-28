package com.krestone.savealife.data.di;


import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.krestone.savealife.data.mappers.NumbersToContactsMapper;
import com.krestone.savealife.data.mappers.PossibleEmergencyContactsFilter;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.repository.ContactsRepositoryImp;
import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.data.repository.EntryRepositoryImp;
import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.data.repository.LocationRepositoryImp;
import com.krestone.savealife.data.repository.MapRepository;
import com.krestone.savealife.data.repository.MapRepositoryImpl;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.data.repository.ProfileRepositoryImpl;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.domain.schedulers.SubscribeOn;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class RepositoriesModule {

    @Provides
    @Singleton
    LocationRepository provideLocationRepository(Context context, ReactiveLocationProvider locationProvider,
                                                 LocationRequest locationRequest, ServerApi serverApi) {
        return new LocationRepositoryImp(context, locationProvider, locationRequest, serverApi);
    }

    @Provides
    @Singleton
    ContactsRepository provideContactsRepository(Context context, ServerApi serverApi,
                                                 NumbersToContactsMapper numbersToContactsMapper,
                                                 PossibleEmergencyContactsFilter possibleEmergencyContactsFilter,
                                                 SubscribeOn subscribeOn) {
        return new ContactsRepositoryImp(context, serverApi, numbersToContactsMapper,
                possibleEmergencyContactsFilter, subscribeOn.getScheduler());
    }

    @Provides
    @Singleton
    EntryRepository provideRegistrationRepository(ServerApi serverApi, Context context) {
        return new EntryRepositoryImp(serverApi, context);
    }

    @Provides
    @Singleton
    MapRepository provideMapRepository(ServerApi serverApi, Context context) {
        return new MapRepositoryImpl(serverApi, context);
    }

    @Provides
    @Singleton
    ProfileRepository provideProfileRepository(ServerApi serverApi, Context context) {
        return new ProfileRepositoryImpl(serverApi, context);
    }
}
