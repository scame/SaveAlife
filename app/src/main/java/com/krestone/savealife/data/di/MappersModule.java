package com.krestone.savealife.data.di;


import com.krestone.savealife.data.mappers.HelpIntentMessageMapper;
import com.krestone.savealife.data.mappers.NotInEmergencyListFilter;
import com.krestone.savealife.data.mappers.RouteModelMapper;
import com.krestone.savealife.data.mappers.SosMessageMapper;

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

    @Provides
    @Singleton
    SosMessageMapper provideSosMessageMapper() {
        return new SosMessageMapper();
    }

    @Provides
    @Singleton
    HelpIntentMessageMapper provideHelpIntentMessageMapper() {
        return new HelpIntentMessageMapper();
    }
}
