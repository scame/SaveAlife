package com.krestone.savealife.presentation.presenters.map;


import com.krestone.savealife.presentation.presenters.Presenter;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

public interface SosWindowPresenter<T> extends Presenter<T> {

    interface SosWindowView {

        void onHelpPressed(PolylineOptions polyline);

        void onError(String error);
    }

    void requestDesireToHelp(LatLng targetLatLng, String number);
}
