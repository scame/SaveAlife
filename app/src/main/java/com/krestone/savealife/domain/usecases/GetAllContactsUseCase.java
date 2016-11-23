package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Observable;

public class GetAllContactsUseCase extends UseCaseObservable<List<ContactModel>> {

    private ContactsRepository contactsRepository;

    public GetAllContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Observable<List<ContactModel>> getUseCaseObservable() {
        return contactsRepository.getContacts();
    }
}
