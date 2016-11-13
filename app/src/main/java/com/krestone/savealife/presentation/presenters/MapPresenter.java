package com.krestone.savealife.presentation.presenters;


import com.mapbox.mapboxsdk.geometry.LatLng;

public interface MapPresenter<T> extends Presenter<T> {

    interface MapView {

        void displayLocation(LatLng latLng);
    }

    void requestLastKnownLocation();
}
