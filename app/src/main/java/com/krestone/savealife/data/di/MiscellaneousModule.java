package com.krestone.savealife.data.di;


import android.content.Context;

import com.google.android.gms.location.LocationRequest;
import com.krestone.savealife.data.messages.NotificationsHandler;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;

@Module
public class MiscellaneousModule {

    @Provides
    @Singleton
    LocationRequest provideLocationRequest() {
        return LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(100);
    }

    @Provides
    @Singleton
    ReactiveLocationProvider provideReactiveLocationProvider(Context context) {
        return new ReactiveLocationProvider(context);
    }

    @Provides
    @Singleton
    NotificationsHandler provideNotificationsHandler(Context context) {
        return new NotificationsHandler(context);
    }
}
