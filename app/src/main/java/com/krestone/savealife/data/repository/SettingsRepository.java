package com.krestone.savealife.data.repository;


import rx.Completable;

public interface SettingsRepository {

    Completable changeMessagesState(boolean isEnabled);

    Completable changeLocationsUpdates(boolean isEnabled);
}
