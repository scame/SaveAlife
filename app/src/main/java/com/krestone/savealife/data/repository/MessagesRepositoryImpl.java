package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.HelpIntentRequest;
import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;

import rx.Completable;

public class MessagesRepositoryImpl implements MessagesRepository {

    private ServerApi serverApi;

    private Context context;

    public MessagesRepositoryImpl(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
    }

    @Override
    public Completable sendStartSosMessage(SosEntity sosEntity) {
        return serverApi.startSos(PrefsUtil.getAuthToken(context), sosEntity).toCompletable();
    }

    @Override
    public Completable sendStopSosMessage(SosEntity sosEntity) {
        return serverApi.stopSos(PrefsUtil.getAuthToken(context), sosEntity).toCompletable();
    }

    @Override
    public Completable sendHelpIntent(String number) {
        return serverApi.helpIntentRequest(PrefsUtil.getAuthToken(context), new HelpIntentRequest(number))
                .toCompletable();
    }
}
