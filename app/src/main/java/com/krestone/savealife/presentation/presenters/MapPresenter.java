package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.mapbox.mapboxsdk.geometry.LatLng;

public interface MapPresenter<T> extends Presenter<T> {

    interface MapView {

        void displayLocation(LatLng latLng);

        void displayMapObjects(MapObjectsEntity mapObjectsEntity);
    }

    void requestLastKnownLocation();

    void requestLocationUpdates();

    void requestMapObjectsUpdates();
}
