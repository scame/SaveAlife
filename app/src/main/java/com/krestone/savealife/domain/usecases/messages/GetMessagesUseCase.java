package com.krestone.savealife.domain.usecases.messages;


import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import java.util.List;

import rx.Single;

public class GetMessagesUseCase extends UseCaseSingle<List<AbstractMessage>> {

    private MessagesRepository messagesRepository;

    public GetMessagesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                              MessagesRepository messagesRepository) {
        super(subscribeOn, observeOn);
        this.messagesRepository = messagesRepository;
    }

    @Override
    protected Single<List<AbstractMessage>> getUseCaseSingle() {
        return messagesRepository.getAllMessages();
    }
}
