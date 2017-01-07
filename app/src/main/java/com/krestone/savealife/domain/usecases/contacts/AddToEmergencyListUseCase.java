package com.krestone.savealife.domain.usecases.contacts;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;
import com.krestone.savealife.presentation.models.ContactModel;

import rx.Completable;

public class AddToEmergencyListUseCase extends UseCaseCompletable {

    private ContactsRepository contactsRepository;

    private ContactModel contactModel;

    public AddToEmergencyListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                     ContactsRepository contactsRepository) {
        super(subscribeOn, observeOn);
        this.contactsRepository = contactsRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return contactsRepository.addToEmergencyList(contactModel);
    }

    public ContactModel getContactModel() {
        return contactModel;
    }

    public void setContactModel(ContactModel contactModel) {
        this.contactModel = contactModel;
    }
}
