package com.krestone.savealife.data.repository;


import android.location.Location;

import rx.Observable;

public interface LocationRepository {

    Observable<Location> getLastKnownLocation();

    Observable<Location> startGettingLocationUpdates();
}
