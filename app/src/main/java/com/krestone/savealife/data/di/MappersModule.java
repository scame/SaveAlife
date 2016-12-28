package com.krestone.savealife.data.di;


import com.krestone.savealife.data.mappers.NumbersToContactsMapper;
import com.krestone.savealife.data.mappers.PossibleEmergencyContactsFilter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

    @Provides
    @Singleton
    NumbersToContactsMapper provideNumbersToContactsMapper() {
        return new NumbersToContactsMapper();
    }

    @Provides
    @Singleton
    PossibleEmergencyContactsFilter providePossibleEmergencyContactsFilter() {
        return new PossibleEmergencyContactsFilter();
    }
}
