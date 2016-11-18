package com.krestone.savealife.presentation.presenters;


import android.location.Location;
import android.util.Log;

import com.krestone.savealife.domain.usecases.DefaultSubscriber;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.domain.usecases.LocationUpdatesUseCase;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class MapPresenterImp<T extends MapPresenter.MapView> implements MapPresenter<T> {

    private LastKnownLocationUseCase lastKnownLocationUseCase;

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private T view;

    public MapPresenterImp(LastKnownLocationUseCase lastKnownLocationUseCase,
                           LocationUpdatesUseCase locationUpdatesUseCase) {
        this.lastKnownLocationUseCase = lastKnownLocationUseCase;
        this.locationUpdatesUseCase = locationUpdatesUseCase;
    }

    @Override
    public void requestLastKnownLocation() {
        lastKnownLocationUseCase.execute(new LastKnowLocationSubscriber());
    }

    @Override
    public void requestLocationUpdates() {
        locationUpdatesUseCase.execute(new LocationUpdateSubscriber());
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
