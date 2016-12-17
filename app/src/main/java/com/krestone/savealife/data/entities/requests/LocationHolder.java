package com.krestone.savealife.data.entities.requests;


public class LocationHolder {

    private double currentLat;

    private double currentLon;

    public LocationHolder() { }

    public LocationHolder(double currentLat, double currentLon) {
        this.currentLat = currentLat;
        this.currentLon = currentLon;
    }

    public void setCurrentLat(double currentLat) {
        this.currentLat = currentLat;
    }

    public void setCurrentLon(double currentLon) {
        this.currentLon = currentLon;
    }

    public double getCurrentLat() {
        return currentLat;
    }

    public double getCurrentLon() {
        return currentLon;
    }
}
