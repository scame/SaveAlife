package com.krestone.savealife.domain.usecases.contacts;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetContactsInAppUseCase extends UseCaseSingle<ContactsNumbersHolder> {

    private ContactsRepository contactsRepository;

    public GetContactsInAppUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                   ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Single<ContactsNumbersHolder> getUseCaseSingle() {
        return contactsRepository.getContactsInApp();
    }
}
