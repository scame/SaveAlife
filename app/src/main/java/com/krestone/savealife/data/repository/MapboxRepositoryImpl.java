package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.R;
import com.krestone.savealife.data.mappers.RouteModelMapper;
import com.krestone.savealife.data.rest.MapboxApi;
import com.krestone.savealife.presentation.models.AddressModel;
import com.krestone.savealife.presentation.models.RouteModel;
import com.krestone.savealife.util.PrefsUtil;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.ServicesException;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.geocoding.v5.GeocodingCriteria;
import com.mapbox.services.geocoding.v5.MapboxGeocoding;
import com.mapbox.services.geocoding.v5.models.CarmenFeature;

import java.io.IOException;
import java.util.List;

import rx.Single;

public class MapboxRepositoryImpl implements MapboxRepository {

    private static final String PROFILE_TYPE = "driving";

    private Context context;

    private MapboxApi mapboxApi;

    private RouteModelMapper routeModelMapper;

    private MapboxGeocoding mapboxGeocodingClient;

    public MapboxRepositoryImpl(MapboxApi mapboxApi, RouteModelMapper routeModelMapper, Context context) {
        this.routeModelMapper = routeModelMapper;
        this.mapboxApi = mapboxApi;
        this.context = context;
    }

    // TODO: better use MapboxDirections
    @Override
    public Single<RouteModel> getRoute(LatLng origin, LatLng dest) {
        return mapboxApi.getRoute(PROFILE_TYPE, formatCoordinates(origin, dest),
                context.getString(R.string.default_access_token))
                .map(routeModelMapper::convert).toSingle();
    }

    @Override
    public Single<RouteModel> getRouteImplicitly(LatLng targetLatLng) {
        return mapboxApi.getRoute(PROFILE_TYPE, formatCoordinates(PrefsUtil.getLatestLatLng(context), targetLatLng),
                context.getString(R.string.default_access_token))
                .map(routeModelMapper::convert).toSingle();
    }

    private String formatCoordinates(LatLng origin, LatLng dest) {
        return origin.getLongitude() + "," + origin.getLatitude() + ";"
                + dest.getLongitude() + "," + dest.getLatitude();
    }

    @Override
    public Single<AddressModel> getHumanReadableAddress(Position position) {
        return Single.defer(() -> Single.just(retrieveFormattedAddress(position)));
    }

    private AddressModel retrieveFormattedAddress(Position position) {
        AddressModel addressModel = new AddressModel();
        constructGeocodingClient(position);
        try {
            List<CarmenFeature> results = mapboxGeocodingClient.executeCall().body().getFeatures();
            if (results.size() > 0) {
                String placeName = results.get(0).getPlaceName();
                addressModel.setFormattedAddress(placeName);
            } else {
                addressModel.setFormattedAddress("No results");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return addressModel;
    }

    // TODO: move into a module
    private void constructGeocodingClient(Position position) {
        try {
            mapboxGeocodingClient = new MapboxGeocoding.Builder()
                    .setAccessToken(context.getString(R.string.default_access_token))
                    .setGeocodingType(GeocodingCriteria.TYPE_ADDRESS)
                    .setGeocodingTypes(new String [] { GeocodingCriteria.TYPE_POI, GeocodingCriteria.TYPE_ADDRESS,
                            GeocodingCriteria.TYPE_NEIGHBORHOOD, GeocodingCriteria.TYPE_PLACE})
                    .setCountries(new String [] {"ua", "us", "gb", "fr", "ru", "de"})
                    .setCoordinates(position)
                    .build();
        } catch (ServicesException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}
