package com.krestone.savealife.domain.usecases.contacts;


import com.krestone.savealife.data.entities.responses.ContactsHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetEmergencyContactsUseCase extends UseCaseSingle<ContactsHolder> {

    private ContactsRepository contactsRepository;

    public GetEmergencyContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository repository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = repository;
    }

    @Override
    protected Single<ContactsHolder> getUseCaseSingle() {
        return contactsRepository.getEmergencyContactsLocal(false);
    }
}
