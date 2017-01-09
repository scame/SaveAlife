package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.contacts.AddToEmergencyListUseCase;
import com.krestone.savealife.domain.usecases.contacts.GetContactsNotInEmergencyList;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.contacts.AddToEmergencyListPresenter;
import com.krestone.savealife.presentation.presenters.contacts.AddToEmergencyListPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.contacts.AddToEmergencyListPresenter.*;

@Module
public class AddToEmergencyListModule {

    @Provides
    @PerActivity
    AddToEmergencyListPresenter<AddToEmergencyListView> provideContactsPresenter(
            GetContactsNotInEmergencyList getContactsNotInEmergencyList,
            AddToEmergencyListUseCase addToEmergencyListUseCase) {
        return new AddToEmergencyListPresenterImp<>(getContactsNotInEmergencyList, addToEmergencyListUseCase);
    }

    @Provides
    @PerActivity
    GetContactsNotInEmergencyList provideGetPossibleEmergencyContacts(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                                      ContactsRepository contactsRepository) {
        return new GetContactsNotInEmergencyList(subscribeOn, observeOn, contactsRepository);
    }

    @Provides
    @PerActivity
    AddToEmergencyListUseCase provideAddToEmergencyListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                               ContactsRepository contactsRepository) {
        return new AddToEmergencyListUseCase(subscribeOn, observeOn, contactsRepository);
    }
}
