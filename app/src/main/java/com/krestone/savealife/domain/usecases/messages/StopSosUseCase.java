package com.krestone.savealife.domain.usecases.messages;


import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class StopSosUseCase extends UseCaseCompletable {

    private MessagesRepository messagesRepository;

    private SosEntity sosEntity;

    public StopSosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, MessagesRepository messagesRepository) {
        super(subscribeOn, observeOn);
        this.messagesRepository = messagesRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return messagesRepository.sendStopSosMessage(sosEntity);
    }

    public SosEntity getSosEntity() {
        return sosEntity;
    }

    public void setSosEntity(SosEntity sosEntity) {
        this.sosEntity = sosEntity;
    }
}
