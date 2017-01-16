package com.krestone.savealife.domain.usecases.contacts;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.data.sync.states.DataStates;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import rx.Completable;

public class DeleteFromEmergencyListUseCase extends UseCaseCompletable {

    private ContactsRepository contactsRepository;

    private List<ContactModel> contacts;

    public DeleteFromEmergencyListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return contactsRepository.updateDataState(contacts, DataStates.REMOVED);
    }

    public List<ContactModel> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactModel> contacts) {
        this.contacts = contacts;
    }
}
