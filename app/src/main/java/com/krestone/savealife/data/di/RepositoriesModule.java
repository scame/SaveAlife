package com.krestone.savealife.data.di;


import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.krestone.savealife.data.mappers.MapContactModelToContactsHolder;
import com.krestone.savealife.data.mappers.NotInEmergencyListFilter;
import com.krestone.savealife.data.mappers.RouteModelMapper;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.repository.ContactsRepositoryImp;
import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.data.repository.EntryRepositoryImp;
import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.data.repository.LocationRepositoryImp;
import com.krestone.savealife.data.repository.MapObjectsRepository;
import com.krestone.savealife.data.repository.MapObjectsRepositoryImpl;
import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.data.repository.MapboxRepositoryImpl;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.data.repository.ProfileRepositoryImpl;
import com.krestone.savealife.data.rest.MapboxApi;
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
                                                 MapContactModelToContactsHolder mapper,
                                                 NotInEmergencyListFilter notInEmergencyListFilter,
                                                 SubscribeOn subscribeOn) {
        return new ContactsRepositoryImp(context, serverApi, notInEmergencyListFilter,
                mapper,subscribeOn.getScheduler());
    }

    @Provides
    @Singleton
    EntryRepository provideRegistrationRepository(ServerApi serverApi, Context context) {
        return new EntryRepositoryImp(serverApi, context);
    }

    @Provides
    @Singleton
    MapObjectsRepository provideMapRepository(ServerApi serverApi, Context context) {
        return new MapObjectsRepositoryImpl(serverApi, context);
    }

    @Provides
    @Singleton
    ProfileRepository provideProfileRepository(ServerApi serverApi, Context context) {
        return new ProfileRepositoryImpl(serverApi, context);
    }

    @Provides
    @Singleton
    MapboxRepository provideMapboxRepository(MapboxApi mapboxApi, Context context, RouteModelMapper mapper) {
        return new MapboxRepositoryImpl(mapboxApi, mapper, context);
    }
}
