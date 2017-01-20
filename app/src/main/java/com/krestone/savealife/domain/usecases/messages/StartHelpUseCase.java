package com.krestone.savealife.domain.usecases.messages;


import com.krestone.savealife.data.entities.responses.map.HelpIntentWrapper;
import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.mapbox.mapboxsdk.geometry.LatLng;

import rx.Single;

public class StartHelpUseCase extends UseCaseSingle<HelpIntentWrapper> {

    private LatLng targetLatLng;

    private String phoneNumber;

    private MapboxRepository mapboxRepository;

    private MessagesRepository messagesRepository;

    public StartHelpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                            MapboxRepository mapboxRepository,
                            MessagesRepository messagesRepository) {
        super(subscribeOn, observeOn);
        this.mapboxRepository = mapboxRepository;
        this.messagesRepository = messagesRepository;
    }

    @Override
    protected Single<HelpIntentWrapper> getUseCaseSingle() {
        return Single.zip(messagesRepository.sendHelpIntent(phoneNumber, true),
                          mapboxRepository.getRouteImplicitly(targetLatLng),
                          HelpIntentWrapper::new);
    }

    public LatLng getTargetLatLng() {
        return targetLatLng;
    }

    public void setTargetLatLng(LatLng targetLatLng) {
        this.targetLatLng = targetLatLng;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
