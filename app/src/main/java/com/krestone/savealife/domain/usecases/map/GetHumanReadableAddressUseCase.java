package com.krestone.savealife.domain.usecases.map;


import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.krestone.savealife.presentation.models.AddressModel;
import com.mapbox.services.commons.models.Position;

import rx.Single;

public class GetHumanReadableAddressUseCase extends UseCaseSingle<AddressModel> {

    private MapboxRepository mapboxRepository;

    private Position position;

    public GetHumanReadableAddressUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                          MapboxRepository mapboxRepository) {
        super(subscribeOn, observeOn);
        this.mapboxRepository = mapboxRepository;
    }

    @Override
    protected Single<AddressModel> getUseCaseSingle() {
        return mapboxRepository.getHumanReadableAddress(position);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
