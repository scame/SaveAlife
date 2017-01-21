package com.krestone.savealife.data.repository;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.entities.responses.map.HelpIntentState;
import com.krestone.savealife.data.mappers.HelpIntentMessageMapper;
import com.krestone.savealife.data.mappers.SosMessageMapper;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.data.sqlite.MessagesTable;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;
import com.krestone.savealife.util.PrefsUtil;

import java.util.Collections;
import java.util.Map;

import rx.Completable;
import rx.Single;

import static com.krestone.savealife.data.sqlite.MessagesTable.MESSAGE_TYPE_INTENT_START;
import static com.krestone.savealife.data.sqlite.MessagesTable.MESSAGE_TYPE_INTENT_STOP;
import static com.krestone.savealife.data.sqlite.MessagesTable.MESSAGE_TYPE_SOS_START;
import static com.krestone.savealife.data.sqlite.MessagesTable.MESSAGE_TYPE_SOS_STOP;

public class MessagesRepositoryImpl implements MessagesRepository {

    private HelpIntentMessageMapper helpIntentMessageMapper;

    private SosMessageMapper sosMessageMapper;

    private MessagesTable messagesTable;

    private ServerApi serverApi;

    private Context context;

    public MessagesRepositoryImpl(ServerApi serverApi, Context context, MessagesTable messagesTable,
                                  HelpIntentMessageMapper helpIntentMessageMapper,
                                  SosMessageMapper sosMessageMapper) {
        this.helpIntentMessageMapper = helpIntentMessageMapper;
        this.sosMessageMapper = sosMessageMapper;
        this.messagesTable = messagesTable;
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

    @Override
    public Completable saveFirebaseMessage(Map<String, String> messageData) {
        int mType = Integer.valueOf(messageData.get(MessagesTable.KEY_MESSAGE_TYPE));

        if (mType == MESSAGE_TYPE_INTENT_START || mType == MESSAGE_TYPE_INTENT_STOP) {
            SosMessageModel sosMessage = sosMessageMapper.convert(messageData);
            messagesTable.addSosMessages(Collections.singletonList(sosMessage));
        } else if (mType == MESSAGE_TYPE_SOS_START || mType == MESSAGE_TYPE_SOS_STOP) {
            HelpIntentMessageModel helpIntentMessage = helpIntentMessageMapper.convert(messageData);
            messagesTable.addHelpIntentMessages(Collections.singletonList(helpIntentMessage));
        }

        return Completable.complete();
    }

    @Override
    public Single<AbstractMessage> parseFirebaseMessage(Map<String, String> messageData) {
        int mType = Integer.valueOf(messageData.get(MessagesTable.KEY_MESSAGE_TYPE));
        AbstractMessage parsedMessage = null;

        if (mType == MESSAGE_TYPE_INTENT_START || mType == MESSAGE_TYPE_INTENT_STOP) {
            parsedMessage = sosMessageMapper.convert(messageData);
        } else if (mType == MESSAGE_TYPE_SOS_START || mType == MESSAGE_TYPE_SOS_STOP) {
            parsedMessage = helpIntentMessageMapper.convert(messageData);
        }

        return Single.just(parsedMessage);
    }
}
