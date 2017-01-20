package com.krestone.savealife.presentation.fragments;


import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.map.MapObject;
import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.map.MapPresenter;
import com.mapbox.mapboxsdk.MapboxAccountManager;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.annotations.Polyline;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.services.android.geocoder.ui.GeocoderAutoCompleteView;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import icepick.State;

public class MapFragment extends AbstractFragment implements MapPresenter.MapView {

    @Inject
    MapPresenter<MapPresenter.MapView> mapPresenter;

    @BindView(R.id.stop_helping_fab)
    FloatingActionButton helpFab;

    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.autocomplete_view)
    GeocoderAutoCompleteView autocompleteView;

    private Polyline helpPolyline;

    private String targetPhoneNumber;

    private MapboxMap mapboxMap;

    private Polyline routePolyline;

    private Marker currentLocationMarker;

    private Marker destinationMarker;

    private final Map<Marker, MapObject> mapObjectsMap = new HashMap<>();

    @State
    LatLng latestPosition;

    @State
    LatLng destinationPosition;

    @State
    MapObjectsEntity mapObjectsEntity;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        initializeMap();
        mapPresenter.setView(this);
        mapView.onCreate(savedInstanceState);
        setupAutocompleteView();

        return fragmentView;
    }

    private void setupOnLongClickListener() {
        mapboxMap.setOnMapLongClickListener(this::updateDestinationPoint);
    }


    private void recomputeDirectionPolyline() {
        if (destinationPosition != null && latestPosition != null) {
            mapPresenter.requestRoute(latestPosition, destinationPosition);
        }
    }

    // FIXME: 11.01.2017 shouldn't recompute every time device' position changes
    private void updateDestinationPoint(LatLng newDestination) {
        this.destinationPosition = newDestination;

        if (destinationMarker != null) {
            destinationMarker.remove();
        }
        destinationMarker = mapboxMap.addMarker(new MarkerOptions().position(newDestination));
        recomputeDirectionPolyline();
    }

    private void initializeMap() {
        mapView.getMapAsync(mapboxMap1 -> {
            MapFragment.this.mapboxMap = mapboxMap1;
            setupOnMarkerClickListener();
            setupOnLongClickListener();

            mapPresenter.requestLocationUpdates();
            mapPresenter.requestMapObjectsUpdates();

            restoreMapObjects();
            if (latestPosition != null) {
                updateCurrentLocation(latestPosition.getLatitude(), latestPosition.getLongitude());
            }
            if (destinationPosition != null) {
                updateDestinationPoint(destinationPosition);
            }
        });
    }


    private void setupOnMarkerClickListener() {
        mapboxMap.setOnMarkerClickListener(marker -> {
            MapObject mappedObject = mapObjectsMap.get(marker);

            if (mappedObject != null && mappedObject.isSos()) {
                displaySosWindow(mappedObject);
                return true;
            }
            // will be shown default info dialog
            return false;
        });
    }

    private void displaySosWindow(MapObject mapObject) {
        getFragmentManager().beginTransaction()
                .addToBackStack(null)
                .add(R.id.drawer_activity_fl, SosWindowFragment.newInstance(mapObject), "TAG")
                .commit();
    }

    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideMapComponent().inject(this);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.map_fragment_layout;
    }

    @Override
    public void displayHumanReadableAddress(String address) {

    }

    @Override
    public void displayRoute(PolylineOptions polylineOptions) {
        if (routePolyline != null) {
            routePolyline.remove();
        }
        routePolyline = mapboxMap.addPolyline(polylineOptions);
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void setupAutocompleteView() {
        autocompleteView.setAccessToken(MapboxAccountManager.getInstance().getAccessToken());
        autocompleteView.setType(GeocodingCriteria.TYPE_POI);
        autocompleteView.setOnFeatureListener(feature -> {
            Position position = feature.asPosition();
            updateDestinationPoint(new LatLng(position.getLatitude(), position.getLongitude()));
        });
    }

    private void updateCurrentLocation(double latitude, double longitude) {
        latestPosition = new LatLng(latitude, longitude);

        if (mapboxMap != null) {
            updateCurrentPositionMarker(latitude, longitude);
            animateCamera(latitude, longitude, 5000, 15);
        }
    }


    private void updateCurrentPositionMarker(double latitude, double longitude) {
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        currentLocationMarker = mapboxMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude)));
    }

    private void animateCamera(double latitude, double longitude, int durationMil, int zoom) {
        CameraPosition cameraPosition = buildCameraPosition(new LatLng(latitude, longitude), zoom);
        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), durationMil, null);
    }

    private CameraPosition buildCameraPosition(LatLng latLng, int zoom) {
        return new CameraPosition.Builder()
                .target(latLng)
                .zoom(zoom)
                .build();
    }

    @Override
    public void displayCurrentLocation(LatLng latLng) {
        updateCurrentLocation(latLng.getLatitude(), latLng.getLongitude());
    }

    @Override
    public void displayMapObjects(MapObjectsEntity mapObjectsEntity) {
        this.mapObjectsEntity = mapObjectsEntity;
        removeOldMapObjects();
        addMapObjectsToMap(mapObjectsEntity);
    }

    private void addMapObjectsToMap(MapObjectsEntity mapObjectsEntity) {
        for (MapObject mapObject : mapObjectsEntity.getMapObjectList()) {
            if (mapObject.isSos()) {
                handleSosCase(mapObject);
            } else {
                handlePlainObject(mapObject);
            }
        }
    }

    // TODO: add status icons
    private void handlePlainObject(MapObject mapObject) {
        if (mapObject.getRole().equals("driver")) {
            addPlainObjectMarker(mapObject, getString(R.string.driver), R.drawable.ic_cancel_black_24dp);
        } else if (mapObject.getRole().equals("person")) {
            addPlainObjectMarker(mapObject, getString(R.string.rambler), R.drawable.ic_arrow_back_black_24dp);
        } else if (mapObject.getRole().equals("ambulance")) {
            addPlainObjectMarker(mapObject, getString(R.string.ambulance), R.drawable.ic_near_me_black_24dp);
        }
    }

    private void addPlainObjectMarker(MapObject plainObject, String title, int markerDrawable) {
        if (mapboxMap != null) {
            Marker marker = mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(plainObject.getLatitude(), plainObject.getLongitude()))
                    .setIcon(getMapboxIcon(markerDrawable))
                    .setTitle(title)
                    .setSnippet(plainObject.getPhoneNumber())
            );

            mapObjectsMap.put(marker, plainObject);
        }
    }


    private void handleSosCase(MapObject mapObject) {
        addSosObjectMarker(mapObject, R.drawable.ic_help_black_24dp);
    }

    private void addSosObjectMarker(MapObject sosObject, int markerDrawable) {
        if (mapboxMap != null) {
            Marker marker = mapboxMap.addMarker(new MarkerOptions()
                    .position(new LatLng(sosObject.getLatitude(), sosObject.getLongitude()))
                    .setIcon(getMapboxIcon(markerDrawable)));

            mapObjectsMap.put(marker, sosObject);
        }
    }

    private void removeOldMapObjects() {
        for (Marker marker : mapObjectsMap.keySet()) {
            marker.remove();
        }
        mapObjectsMap.clear();
    }

    private Icon getMapboxIcon(int drawableId) {
        IconFactory iconFactory = IconFactory.getInstance(getContext());
        Drawable iconDrawable = ContextCompat.getDrawable(getContext(), drawableId);
        return iconFactory.fromDrawable(iconDrawable);
    }

    @OnClick(R.id.stop_helping_fab)
    void onStopHelpingClick(View v) {
        if (targetPhoneNumber != null) {
            mapPresenter.requestStopHelping(targetPhoneNumber);
        }
    }

    @Override
    public void onStopHelping() {
        if (helpPolyline != null) {
            helpPolyline.remove();
            helpFab.setVisibility(View.INVISIBLE);
        }
    }

    public void onHelpRouteBuilt(PolylineOptions helpRoute, String targetPhoneNumber) {
        if (helpPolyline != null) {
            helpPolyline.remove();
        }
        this.targetPhoneNumber = targetPhoneNumber;
        helpPolyline = mapboxMap.addPolyline(helpRoute);

        helpFab.setVisibility(View.VISIBLE);
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
    public void onDestroyView() {
        mapView.onDestroy();
        mapPresenter.destroy();
        super.onDestroyView();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    private void restoreMapObjects() {
        if (mapObjectsEntity != null) {
            addMapObjectsToMap(mapObjectsEntity);
        }
    }
}
