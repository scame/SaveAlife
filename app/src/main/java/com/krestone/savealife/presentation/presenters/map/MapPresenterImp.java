package com.krestone.savealife.presentation.presenters.map;


import android.location.Location;
import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.krestone.savealife.domain.usecases.GetMapObjectsUseCase;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.domain.usecases.LocationUpdatesUseCase;
import com.krestone.savealife.domain.usecases.base.DefaultSubscriber;
import com.krestone.savealife.domain.usecases.map.GetHumanReadableAddressUseCase;
import com.krestone.savealife.domain.usecases.map.GetRouteUseCase;
import com.krestone.savealife.domain.usecases.messages.StopHelpUseCase;
import com.krestone.savealife.util.ConnectivityUtil;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.models.Position;

public class MapPresenterImp<T extends MapPresenter.MapView> implements MapPresenter<T> {

    private static final double updateArea = 100;

    private static final int updateIntervalInSec = 5;

    private LastKnownLocationUseCase lastKnownLocationUseCase;

    private LocationUpdatesUseCase locationUpdatesUseCase;

    private GetMapObjectsUseCase mapObjectsUseCase;

    private GetHumanReadableAddressUseCase getHumanReadableAddressUseCase;

    private GetRouteUseCase getRouteUseCase;

    private StopHelpUseCase stopHelpUseCase;

    private T view;

    public MapPresenterImp(LastKnownLocationUseCase lastKnownLocationUseCase,
                           LocationUpdatesUseCase locationUpdatesUseCase,
                           GetMapObjectsUseCase mapObjectsUseCase,
                           GetHumanReadableAddressUseCase getHumanReadableAddressUseCase,
                           GetRouteUseCase getRouteUseCase,
                           StopHelpUseCase stopHelpUseCase) {
        this.lastKnownLocationUseCase = lastKnownLocationUseCase;
        this.locationUpdatesUseCase = locationUpdatesUseCase;
        this.mapObjectsUseCase = mapObjectsUseCase;
        this.getHumanReadableAddressUseCase = getHumanReadableAddressUseCase;
        this.getRouteUseCase = getRouteUseCase;
        this.stopHelpUseCase = stopHelpUseCase;
    }

    @Override
    public void requestMapObjectsUpdates() {
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
    public void requestHumanReadableAddress(Position position) {
        getHumanReadableAddressUseCase.setPosition(position);
        getHumanReadableAddressUseCase.executeSingle(addressModel -> {
            if (view != null) {
                view.displayHumanReadableAddress(addressModel.getFormattedAddress());
            }
        }, throwable -> {
            if (view != null) {
                view.onError(throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void requestRoute(LatLng origin, LatLng dest) {
        getRouteUseCase.setEndpoints(origin, dest);
        getRouteUseCase.executeSingle(routeModel -> {
            if (view != null) {
                view.displayRoute(routeModel.getPolyline());
            }
        }, throwable -> {
            if (view != null) {
                view.onError(throwable.getLocalizedMessage());
            }
        });
    }

    @Override
    public void requestStopHelping(String phoneNumber) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            stopHelpUseCase.setPhoneNumber(phoneNumber);
            requestStopHelping();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void requestStopHelping() {
        stopHelpUseCase.executeCompletable(() -> {
            if (view != null) {
                view.onStopHelping();
            }
        }, throwable -> {
            if (view != null) {
                view.onError(throwable.toString());
            }
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        getHumanReadableAddressUseCase.unsubscribe();
        lastKnownLocationUseCase.unsubscribe();
        locationUpdatesUseCase.unsubscribe();
        getRouteUseCase.unsubscribe();
        stopHelpUseCase.unsubscribe();
        mapObjectsUseCase.unsubscribe();
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
                view.displayCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
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
                view.displayCurrentLocation(new LatLng(location.getLatitude(), location.getLongitude()));
            }
        }
    }
}
