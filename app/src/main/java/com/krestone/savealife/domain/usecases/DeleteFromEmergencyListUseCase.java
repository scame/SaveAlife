package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.requests.ContactsNumbersHolder;
import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class DeleteFromEmergencyListUseCase extends UseCaseCompletable {

    private ContactsRepository contactsRepository;

    private ContactsNumbersHolder contactsNumbersHolder;

    public DeleteFromEmergencyListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return contactsRepository.deleteFromEmergencyList(contactsNumbersHolder);
    }

    public ContactsNumbersHolder getContactsNumbersHolder() {
        return contactsNumbersHolder;
    }

    public void setContactsNumbersHolder(ContactsNumbersHolder contactsNumbersHolder) {
        this.contactsNumbersHolder = contactsNumbersHolder;
    }
}
