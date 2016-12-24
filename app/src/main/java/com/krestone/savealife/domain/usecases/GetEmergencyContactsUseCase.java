package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetEmergencyContactsUseCase extends UseCaseSingle<ContactsNumbersHolder> {

    private ContactsRepository contactsRepository;

    public GetEmergencyContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository repository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = repository;
    }

    @Override
    protected Single<ContactsNumbersHolder> getUseCaseSingle() {
        return contactsRepository.getEmergencyContacts();
    }
}
