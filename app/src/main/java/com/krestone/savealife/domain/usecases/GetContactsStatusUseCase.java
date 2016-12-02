package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.responses.ContactsStatusEntity;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetContactsStatusUseCase extends UseCaseSingle<ContactsStatusEntity> {

    private ContactsRepository contactsRepository;

    public GetContactsStatusUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                    ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Single<ContactsStatusEntity> getUseCaseSingle() {
        return contactsRepository.getContactsStatus();
    }
}
