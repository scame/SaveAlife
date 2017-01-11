package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.models.Position;

public interface MapPresenter<T> extends Presenter<T> {

    interface MapView {

        void displayCurrentLocation(LatLng latLng);

        void displayMapObjects(MapObjectsEntity mapObjectsEntity);

        void displayHumanReadableAddress(String address);

        void displayRoute(PolylineOptions polylineOptions);

        void onError(String error);
    }

    void requestLastKnownLocation();

    void requestLocationUpdates();

    void requestMapObjectsUpdates();

    void requestHumanReadableAddress(Position position);

    void requestRoute(LatLng origin, LatLng dest);
}
