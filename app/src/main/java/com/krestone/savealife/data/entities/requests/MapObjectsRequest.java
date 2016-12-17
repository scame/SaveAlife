package com.krestone.savealife.data.entities.requests;


public class MapObjectsRequest {

    private double currentLat;

    private double currentLon;

    private double area;

    public MapObjectsRequest(double currentLat, double currentLon, double area) {
        this.currentLat = currentLat;
        this.currentLon = currentLon;
        this.area = area;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }

    public double getArea() {
        return area;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public void setArea(double area) {
        this.area = area;
    }
}
