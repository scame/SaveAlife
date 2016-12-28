package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.contacts.AddToEmergencyListUseCase;
import com.krestone.savealife.domain.usecases.contacts.GetPossibleEmergencyContactsUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.AddToEmergencyListPresenter;
import com.krestone.savealife.presentation.presenters.AddToEmergencyListPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.AddToEmergencyListPresenter.*;

@Module
public class AddToEmergencyListModule {

    @Provides
    @PerActivity
    AddToEmergencyListPresenter<AddToEmergencyListView> provideContactsPresenter(
            GetPossibleEmergencyContactsUseCase getPossibleEmergencyContactsUseCase,
            AddToEmergencyListUseCase addToEmergencyListUseCase) {
        return new AddToEmergencyListPresenterImp<>(getPossibleEmergencyContactsUseCase, addToEmergencyListUseCase);
    }

    @Provides
    @PerActivity
    GetPossibleEmergencyContactsUseCase provideGetPossibleEmergencyContacts(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                                            ContactsRepository contactsRepository) {
        return new GetPossibleEmergencyContactsUseCase(subscribeOn, observeOn, contactsRepository);
    }

    @Provides
    @PerActivity
    AddToEmergencyListUseCase provideAddToEmergencyListUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                               ContactsRepository contactsRepository) {
        return new AddToEmergencyListUseCase(subscribeOn, observeOn, contactsRepository);
    }
}
