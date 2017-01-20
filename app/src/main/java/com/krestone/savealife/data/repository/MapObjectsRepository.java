package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.responses.map.MapObjectsEntity;

import rx.Observable;

public interface MapObjectsRepository {

    void setUpdateArea(double updateArea);

    void setUpdateIntervalInSec(int updateIntervalInSec);

    double getUpdateArea();

    int getUpdateIntervalInSec();

    Observable<MapObjectsEntity> getMapObjects();
}
