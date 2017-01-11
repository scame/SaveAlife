package com.krestone.savealife.domain.usecases.map;


import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Single;

public class GetRouteUseCase extends UseCaseSingle<RouteModel> {

    private LatLng origin;
    private LatLng dest;

    private MapboxRepository mapboxRepository;

    public GetRouteUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, MapboxRepository mapboxRepository) {
        super(subscribeOn, observeOn);
        this.mapboxRepository = mapboxRepository;
    }

    @Override
    protected Single<RouteModel> getUseCaseSingle() {
        return mapboxRepository.getRoute(origin, dest);
    }

    public void setEndpoints(LatLng origin, LatLng dest) {
        this.origin = origin;
        this.dest = dest;
    }

    public LatLng getOrigin() {
        return origin;
    }

    public LatLng getDest() {
        return dest;
    }
}
