package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.entities.responses.map.HelpIntentState;

import rx.Completable;
import rx.Single;

public interface MessagesRepository {

    Completable sendStartSosMessage(SosEntity sosEntity);

    Completable sendStopSosMessage(SosEntity sosEntity);

    Single<HelpIntentState> sendHelpIntent(String number, boolean isHelp);
}
