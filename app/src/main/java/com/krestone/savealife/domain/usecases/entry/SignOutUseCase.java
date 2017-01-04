package com.krestone.savealife.domain.usecases.entry;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class SignOutUseCase extends UseCaseCompletable {

    private EntryRepository entryRepository;

    public SignOutUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository entryRepository) {
        super(subscribeOn, observeOn);
        this.entryRepository = entryRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return entryRepository.signOut();
    }
}
