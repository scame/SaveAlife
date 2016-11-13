package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.presenters.MapPresenter;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment implements MapPresenter.MapView {

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.autocomplete_view)
    GeocoderAutoCompleteView autocompleteView;

    private MapboxMap mapboxMap;

    private MapPresenter<MapPresenter.MapView> mapPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.map_fragment_layout, container, false);

        ButterKnife.bind(this, fragmentView);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(mapboxMap1 -> MapFragment.this.mapboxMap = mapboxMap1);
        setupAutocompleteView();

        return fragmentView;
    }

    private void setupAutocompleteView() {
        autocompleteView.setAccessToken(MapboxAccountManager.getInstance().getAccessToken());
        autocompleteView.setType(GeocodingCriteria.TYPE_POI);
        autocompleteView.setOnFeatureListener(feature -> {
            Position position = feature.asPosition();
            updateMap(position.getLatitude(), position.getLongitude());
        });
    }

    private void updateMap(double latitude, double longitude) {
        mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(latitude, longitude))
                .zoom(15)
                .build();
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 5000, null);
    }

    @Override
    public void displayLocation(LatLng latLng) {
        updateMap(latLng.getLatitude(), latLng.getLongitude());
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
}
