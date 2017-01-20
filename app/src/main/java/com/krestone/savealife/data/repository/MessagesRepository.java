package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.SosEntity;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Completable;

public interface MessagesRepository {

    Completable sendStartSosMessage(SosEntity sosEntity);

    Completable sendStopSosMessage(SosEntity sosEntity);

    Completable sendHelpIntent(LatLng origin, LatLng dest, String number);
}
