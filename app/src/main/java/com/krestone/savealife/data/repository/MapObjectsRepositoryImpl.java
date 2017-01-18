package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.HelpIntentRequest;
import com.krestone.savealife.data.entities.requests.MapObjectsRequest;
import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Observable;

public class MapObjectsRepositoryImpl implements MapObjectsRepository {

    private ServerApi serverApi;

    private Context context;

    private int updateIntervalSec;

    private double updateArea;

    public MapObjectsRepositoryImpl(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
    }

    @Override
    public Observable<MapObjectsEntity> getMapObjects() {
        return Observable.interval(updateIntervalSec, TimeUnit.SECONDS)
                .flatMap(clockTick -> serverApi
                        .sendMapObjectsRequest(getMapObjectsRequest(), PrefsUtil.getAuthToken(context))
                        .retry(3));
    }

    private MapObjectsRequest getMapObjectsRequest() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        double latitude = sharedPrefs.getFloat(context.getString(R.string.latitude_key), (float) 0.0);
        double longitude = sharedPrefs.getFloat(context.getString(R.string.longitude_key), (float) 0.0);
        return new MapObjectsRequest(latitude, longitude, updateArea);
    }

    @Override
    public Completable postHelpRequest(LatLng origin, LatLng dest, String number) {
        return serverApi.helpIntentRequest(PrefsUtil.getAuthToken(context), new HelpIntentRequest(number))
                .toCompletable();
    }

    @Override
    public void setUpdateArea(double updateArea) {
        this.updateArea = updateArea;
    }

    @Override
    public void setUpdateIntervalInSec(int updateIntervalInSec) {
        this.updateIntervalSec = updateIntervalInSec;
    }

    @Override
    public double getUpdateArea() {
        return updateArea;
    }

    @Override
    public int getUpdateIntervalInSec() {
        return updateIntervalSec;
    }
}
