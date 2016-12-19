package com.krestone.savealife.data.entities.responses;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class MapObjectsEntity implements Parcelable {

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.mapObjectList);
    }

    protected MapObjectsEntity(Parcel in) {
        this.mapObjectList = new ArrayList<MapObject>();
        in.readList(this.mapObjectList, MapObject.class.getClassLoader());
    }

    public static final Parcelable.Creator<MapObjectsEntity> CREATOR = new Parcelable.Creator<MapObjectsEntity>() {
        @Override
        public MapObjectsEntity createFromParcel(Parcel source) {
            return new MapObjectsEntity(source);
        }

        @Override
        public MapObjectsEntity[] newArray(int size) {
            return new MapObjectsEntity[size];
        }
    };
}
