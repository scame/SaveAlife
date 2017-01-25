package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.LocationService;
import com.krestone.savealife.data.messages.MyFirebaseMessagingService;

import rx.Completable;

public class SettingsRepositoryImpl implements SettingsRepository {

    private Context context;

    public SettingsRepositoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public Completable changeMessagesState(boolean isEnabled) {
        return MyFirebaseMessagingService.changesMessagingServiceState(isEnabled);
    }

    @Override
    public Completable changeLocationsUpdates(boolean isEnabled) {
        if (isEnabled) {
            LocationService.startGettingLocationUpdates(context);
        } else {
            LocationService.stopGettingLocationUpdates();
        }
        return Completable.complete();
    }
}
