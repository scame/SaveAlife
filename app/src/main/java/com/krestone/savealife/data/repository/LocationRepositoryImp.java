package com.krestone.savealife.data.repository;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;

public class LocationRepositoryImp implements LocationRepository {

    private Context context;

    public LocationRepositoryImp(Context context) {
        this.context = context;
    }

    @Override
    public Observable<Location> getLastKnownLocation() {
        ReactiveLocationProvider reactiveLocationProvider = new ReactiveLocationProvider(context);
        if (checkPermission()) {
            return reactiveLocationProvider.getLastKnownLocation();
        } else {
            throw new RuntimeException("noPermission");
        }
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
