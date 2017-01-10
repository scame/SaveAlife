package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.R;
import com.krestone.savealife.data.mappers.RouteModelMapper;
import com.krestone.savealife.data.rest.MapboxApi;
import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Single;

public class MapboxRepositoryImpl implements MapboxRepository {

    private static final String PROFILE_TYPE = "driving";

    private Context context;

    private MapboxApi mapboxApi;

    private RouteModelMapper routeModelMapper;

    public MapboxRepositoryImpl(MapboxApi mapboxApi, RouteModelMapper routeModelMapper, Context context) {
        this.routeModelMapper = routeModelMapper;
        this.mapboxApi = mapboxApi;
        this.context = context;
    }

    @Override
    public Single<RouteModel> getRoute(LatLng origin, LatLng dest) {
        return mapboxApi.getRoute(PROFILE_TYPE, formatCoordinates(origin, dest),
                context.getString(R.string.default_access_token))
                .map(routeModelMapper::convert).toSingle();
    }

    private String formatCoordinates(LatLng origin, LatLng dest) {
        return origin.getLongitude() + "," + origin.getLatitude() + ";"
                + dest.getLongitude() + "," + dest.getLatitude();
    }
}
