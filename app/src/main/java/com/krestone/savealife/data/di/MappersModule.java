package com.krestone.savealife.data.di;


import com.krestone.savealife.data.mappers.MapContactModelToContactsHolder;
import com.krestone.savealife.data.mappers.NotInEmergencyListFilter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

    @Provides
    @Singleton
    MapContactModelToContactsHolder provideMapContactModelToContactsHolder() {
        return new MapContactModelToContactsHolder();
    }

    @Provides
    @Singleton
    NotInEmergencyListFilter providePossibleEmergencyContactsFilter() {
        return new NotInEmergencyListFilter();
    }
}
