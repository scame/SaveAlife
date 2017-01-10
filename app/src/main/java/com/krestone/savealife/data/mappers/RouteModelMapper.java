package com.krestone.savealife.data.mappers;


import android.graphics.Color;

import com.krestone.savealife.data.entities.responses.map.RoutesEntity;
import com.krestone.savealife.presentation.models.RouteModel;
import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.services.commons.models.Position;
import com.mapbox.services.commons.utils.PolylineUtils;

import java.util.ArrayList;
import java.util.List;

public class RouteModelMapper {

    private static final int FIRST_ROUTE = 0;

    private static final int PRECISION = 5;

    public RouteModel convert(RoutesEntity routesEntity) {
        String encodedPolyline = routesEntity.getRoutes().get(FIRST_ROUTE).getGeometry();
        List<Position> positionList = PolylineUtils.decode(encodedPolyline, PRECISION);

        RouteModel routeModel = new RouteModel();
        routeModel.setPoints(convertToLatLngList(positionList));
        routeModel.setPolyline(getPolyline(positionList));

        return routeModel;
    }

    private PolylineOptions getPolyline(List<Position> pointList) {
        return new PolylineOptions()
                .addAll(convertToLatLngList(pointList))
                .color(Color.RED)
                .width((float) 3);
    }

    private List<LatLng> convertToLatLngList(List<Position> positions) {
        List<LatLng> latLngList = new ArrayList<>();

        for (Position position : positions) {
            latLngList.add(new LatLng(position.getLatitude(), position.getLongitude()));
        }
        return latLngList;
    }
}
