package com.krestone.savealife.domain.usecases.entry;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.krestone.savealife.presentation.models.UserModel;

import rx.Single;

public class GetLastLoggedInUserInfoUseCase extends UseCaseSingle<UserModel> {

    private EntryRepository entryRepository;

    public GetLastLoggedInUserInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository entryRepository) {
        super(subscribeOn, observeOn);
        this.entryRepository = entryRepository;
    }

    @Override
    protected Single<UserModel> getUseCaseSingle() {
        return entryRepository.getLastLoggedInUserInfo();
    }
}
