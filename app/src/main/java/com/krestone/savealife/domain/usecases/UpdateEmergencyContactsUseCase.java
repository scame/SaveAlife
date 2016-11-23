package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;

public class UpdateEmergencyContactsUseCase extends UseCaseCompletable {

    private ContactsRepository contactsRepository;

    private List<ContactModel> contacts;

    public UpdateEmergencyContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository repository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = repository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return contactsRepository.updateEmergencyContacts(contacts);
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }
}
