package com.krestone.savealife.presentation.presenters;


import android.location.Location;
import android.util.Log;

import com.krestone.savealife.data.entities.responses.MapObjectsEntity;
import com.krestone.savealife.domain.usecases.GetMapObjectsUseCase;
import com.krestone.savealife.domain.usecases.base.DefaultSubscriber;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.domain.usecases.LocationUpdatesUseCase;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class MapPresenterImp<T extends MapPresenter.MapView> implements MapPresenter<T> {

    private static final double updateArea = 100;

    private static final int updateIntervalInSec = 5;

    private LastKnownLocationUseCase lastKnownLocationUseCase;

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private GetMapObjectsUseCase mapObjectsUseCase;

    private T view;

    public MapPresenterImp(LastKnownLocationUseCase lastKnownLocationUseCase,
                           LocationUpdatesUseCase locationUpdatesUseCase,
                           GetMapObjectsUseCase mapObjectsUseCase) {
        this.lastKnownLocationUseCase = lastKnownLocationUseCase;
        this.locationUpdatesUseCase = locationUpdatesUseCase;
        this.mapObjectsUseCase = mapObjectsUseCase;
    }

    @Override
    public void requestMapObjects() {
        mapObjectsUseCase.setUpdateArea(updateArea);
        mapObjectsUseCase.setUpdateIntervalSec(updateIntervalInSec);
        mapObjectsUseCase.executeObservable(new MapObjectsSubscriber());
    }

    @Override
    public void requestLastKnownLocation() {
        lastKnownLocationUseCase.executeObservable(new LastKnowLocationSubscriber());
    }

    @Override
    public void requestLocationUpdates() {
        locationUpdatesUseCase.executeObservable(new LocationUpdateSubscriber());
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        lastKnownLocationUseCase.unsubscribe();
        locationUpdatesUseCase.unsubscribe();
        view = null;
    }

    private final class MapObjectsSubscriber extends DefaultSubscriber<MapObjectsEntity> {

        @Override
        public void onNext(MapObjectsEntity mapObjectsEntity) {
            super.onNext(mapObjectsEntity);
            if (view != null) {
                view.displayMapObjects(mapObjectsEntity);
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxMapObjectsErr", e.getLocalizedMessage());
        }
    }

    private final class LocationUpdateSubscriber extends DefaultSubscriber<Location> {

        @Override
        public void onNext(Location location) {
            super.onNext(location);

            if (view != null) {
                view.displayLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxLocationUpdatesErr", e.getLocalizedMessage());
        }
    }

    private final class LastKnowLocationSubscriber extends DefaultSubscriber<Location> {

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxLastKnownLocErr", e.getLocalizedMessage());
        }

        @Override
        public void onNext(Location location) {
            super.onNext(location);

            if (view != null) {
                view.displayLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }
    }
}
