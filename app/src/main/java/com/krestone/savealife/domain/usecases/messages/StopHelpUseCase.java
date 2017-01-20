package com.krestone.savealife.domain.usecases.messages;


import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class StopHelpUseCase extends UseCaseCompletable {

    private MessagesRepository messagesRepository;

    private String phoneNumber;

    public StopHelpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                           MessagesRepository messagesRepository) {
        super(subscribeOn, observeOn);
        this.messagesRepository = messagesRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return messagesRepository.sendHelpIntent(phoneNumber, false).toCompletable();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
