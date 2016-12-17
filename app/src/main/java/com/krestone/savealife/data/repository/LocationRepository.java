package com.krestone.savealife.data.repository;


import android.location.Location;

import com.krestone.savealife.data.entities.requests.LocationHolder;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.Single;

public interface LocationRepository {

    Observable<Location> getLastKnownLocation();

    Observable<Location> startGettingLocationUpdates();

    Single<ResponseBody> sendLocationToServer(LocationHolder locationHolder);
}
