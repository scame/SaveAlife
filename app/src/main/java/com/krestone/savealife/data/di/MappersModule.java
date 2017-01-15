package com.krestone.savealife.data.di;


import com.krestone.savealife.data.mappers.NotInEmergencyListFilter;
import com.krestone.savealife.data.mappers.RouteModelMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MappersModule {

    @Provides
    @Singleton
    NotInEmergencyListFilter providePossibleEmergencyContactsFilter() {
        return new NotInEmergencyListFilter();
    }

    @Provides
    @Singleton
    RouteModelMapper provideRouteModelMapper() {
        return new RouteModelMapper();
    }
}
