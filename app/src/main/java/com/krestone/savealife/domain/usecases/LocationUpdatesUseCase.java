package com.krestone.savealife.domain.usecases;


import android.location.Location;

import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseStream;

import rx.Observable;

public class LocationUpdatesUseCase extends UseCaseStream<Location> {

    private LocationRepository locationRepository;

    public LocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, LocationRepository locationRepository) {
        super(subscribeOn, observeOn);
        this.locationRepository = locationRepository;
    }

    @Override
    protected Observable<Location> getUseCaseObservable() {
        return locationRepository.startGettingLocationUpdates();
    }
}
