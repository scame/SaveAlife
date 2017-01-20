package com.krestone.savealife.data.repository;


import com.krestone.savealife.presentation.models.AddressModel;
import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.models.Position;

import rx.Single;

public interface MapboxRepository {

    Single<RouteModel> getRoute(LatLng origin, LatLng dest);

    Single<RouteModel> getRouteImplicitly(LatLng targetLatLng);

    Single<AddressModel> getHumanReadableAddress(Position position);
}
