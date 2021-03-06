package com.krestone.savealife.presentation.models;


import com.mapbox.mapboxsdk.annotations.PolylineOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class RouteModel {

    private List<LatLng> points;

    private PolylineOptions polyline;

    public void setPoints(List<LatLng> points) {
        this.points = points;
    }

    public void setPolyline(PolylineOptions polyline) {
        this.polyline = polyline;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public PolylineOptions getPolyline() {
        return polyline;
    }
}
