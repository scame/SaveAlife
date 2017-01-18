package com.krestone.savealife.domain.usecases.map;


import com.krestone.savealife.data.repository.MapObjectsRepository;
import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Single;

public class HelpUseCase extends UseCaseSingle<RouteModel> {

    private LatLng origin;

    private LatLng dest;

    private String phoneNumber;

    private MapObjectsRepository mapObjectsRepository;

    private MapboxRepository mapboxRepository;

    public HelpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                       MapObjectsRepository mapObjectsRepository,
                       MapboxRepository mapboxRepository) {
        super(subscribeOn, observeOn);
        this.mapObjectsRepository = mapObjectsRepository;
        this.mapboxRepository = mapboxRepository;
    }

    @Override
    protected Single<RouteModel> getUseCaseSingle() {
        return mapObjectsRepository.postHelpRequest(origin, dest, phoneNumber)
                .toSingle(() -> mapboxRepository.getRoute(origin, dest))
                .flatMap(routeModelSingle -> routeModelSingle);
    }

    public LatLng getOrigin() {
        return origin;
    }

    public LatLng getDest() {
        return dest;
    }

    public void setEndpoints(LatLng origin, LatLng dest) {
        this.origin = origin;
        this.dest = dest;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
