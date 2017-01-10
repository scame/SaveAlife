package com.krestone.savealife.data.repository;


import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Single;

public interface MapboxRepository {

    Single<RouteModel> getRoute(LatLng origin, LatLng dest);
}
