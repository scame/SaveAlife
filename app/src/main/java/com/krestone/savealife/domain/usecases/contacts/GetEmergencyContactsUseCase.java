package com.krestone.savealife.domain.usecases.contacts;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Single;

public class GetEmergencyContactsUseCase extends UseCaseSingle<List<ContactModel>> {

    private ContactsRepository contactsRepository;

    public GetEmergencyContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository repository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = repository;
    }

    @Override
    protected Single<List<ContactModel>> getUseCaseSingle() {
        return contactsRepository.getEmergencyContacts();
    }
}
