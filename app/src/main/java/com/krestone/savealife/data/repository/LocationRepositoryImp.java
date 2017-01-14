package com.krestone.savealife.data.repository;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.LocationRequest;
import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;

import okhttp3.ResponseBody;
import pl.charmas.android.reactivelocation.ReactiveLocationProvider;
import rx.Observable;
import rx.Single;

public class LocationRepositoryImp implements LocationRepository {

    private Context context;

    private ReactiveLocationProvider locationProvider;

    private LocationRequest locationRequest;

    private ServerApi serverApi;

    public LocationRepositoryImp(Context context, ReactiveLocationProvider locationProvider,
                                 LocationRequest locationRequest, ServerApi serverApi) {
        this.context = context;
        this.serverApi = serverApi;
        this.locationProvider = locationProvider;
        this.locationRequest = locationRequest;
    }

    @Override
    public Single<ResponseBody> sendLocationToServer(LocationHolder locationHolder) {
        return serverApi.sendLocation(locationHolder, PrefsUtil.getAuthToken(context)).toSingle();
    }

    @Override
    public Observable<Location> getLastKnownLocation() {
        return checkPermission() ? locationProvider.getLastKnownLocation()
                                 : Observable.error(new RuntimeException("neededPermissions"));
    }

    @Override
    public Observable<Location> startGettingLocationUpdates() {
        return checkPermission() ? locationProvider.getUpdatedLocation(locationRequest).map(this::cacheLocation)
                                 : Observable.error(new RuntimeException("neededPermissions"));
    }

    private boolean checkPermission() {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }

    private Location cacheLocation(Location location) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPrefs.edit().putFloat(context.getString(R.string.latitude_key), (float) location.getLatitude()).apply();
        sharedPrefs.edit().putFloat(context.getString(R.string.longitude_key), (float) location.getLongitude()).apply();
        return location;
    }
}
