package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.entities.responses.map.HelpIntentState;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;

import rx.Completable;
import rx.Single;

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
    public Single<HelpIntentState> sendHelpIntent(String number, boolean isHelp) {
        return serverApi.helpIntentRequest(PrefsUtil.getAuthToken(context), number, isHelp)
                .toSingle();
    }
}
