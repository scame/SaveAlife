package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.SosEntity;

import rx.Completable;

public interface MessagesRepository {

    Completable sendStartSosMessage(SosEntity sosEntity);

    Completable sendStopSosMessage(SosEntity sosEntity);

    Completable sendHelpIntent(String number);
}
