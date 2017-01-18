package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Completable;
import rx.Observable;

public interface MapObjectsRepository {

    void setUpdateArea(double updateArea);

    void setUpdateIntervalInSec(int updateIntervalInSec);

    double getUpdateArea();

    int getUpdateIntervalInSec();

    Observable<MapObjectsEntity> getMapObjects();

    Completable postHelpRequest(LatLng origin, LatLng dest, String number);
}
