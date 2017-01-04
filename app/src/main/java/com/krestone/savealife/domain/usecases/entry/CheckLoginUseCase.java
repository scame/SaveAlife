package com.krestone.savealife.domain.usecases.entry;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class CheckLoginUseCase extends UseCaseSingle<Boolean> {

    private EntryRepository entryRepository;

    public CheckLoginUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository entryRepository) {
        super(subscribeOn, observeOn);
        this.entryRepository = entryRepository;
    }

    @Override
    protected Single<Boolean> getUseCaseSingle() {
        return entryRepository.getLoginStatus();
    }
}
