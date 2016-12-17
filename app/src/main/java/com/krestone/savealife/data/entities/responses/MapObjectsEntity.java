package com.krestone.savealife.data.entities.responses;


import java.util.ArrayList;
import java.util.List;

public class MapObjectsEntity {

    private List<MapObject> mapObjectList;

    public MapObjectsEntity() {
        mapObjectList = new ArrayList<>();
    }

    public void setMapObjectList(List<MapObject> mapObjectList) {
        this.mapObjectList = mapObjectList;
    }

    public List<MapObject> getMapObjectList() {
        return mapObjectList;
    }
}
