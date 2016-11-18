package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.GetAllContactsUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.ContactsPresenter;
import com.krestone.savealife.presentation.presenters.ContactsPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class ContactsModule {

    @Provides
    @PerActivity
    ContactsPresenter<ContactsPresenter.ContactsView> provideContactsPresenter(GetAllContactsUseCase allContactsUseCase) {
        return new ContactsPresenterImp<>(allContactsUseCase);
    }

    @Provides
    @PerActivity
    GetAllContactsUseCase provideAllContactsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository contactsRepository) {
        return new GetAllContactsUseCase(subscribeOn, observeOn, contactsRepository);
    }
}
