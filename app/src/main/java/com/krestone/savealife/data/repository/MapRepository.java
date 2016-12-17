package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.responses.MapObjectsEntity;

import rx.Observable;

public interface MapRepository {

    void setUpdateArea(double updateArea);

    void setUpdateIntervalInSec(int updateIntervalInSec);

    double getUpdateArea();

    int getUpdateIntervalInSec();

    Observable<MapObjectsEntity> getMapObjects();
}
