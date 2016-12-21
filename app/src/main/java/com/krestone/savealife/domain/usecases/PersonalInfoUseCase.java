package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class PersonalInfoUseCase extends UseCaseCompletable {

    private EntryRepository entryRepository;

    private PersonalInfoHolder personalInfoHolder;

    public PersonalInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                               EntryRepository entryRepository) {
        super(subscribeOn, observeOn);
        this.entryRepository = entryRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return entryRepository.sendPersonalInfo(personalInfoHolder);
    }


    public void setPersonalInfoHolder(PersonalInfoHolder personalInfoHolder) {
        this.personalInfoHolder = personalInfoHolder;
    }

    public PersonalInfoHolder getPersonalInfoHolder() {
        return personalInfoHolder;
    }
}
