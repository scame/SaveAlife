package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.ContactsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.GetEmergencyContactsUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.EmergencyPresenter;
import com.krestone.savealife.presentation.presenters.EmergencyPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class EmergencyModule {

    @Provides
    @PerActivity
    EmergencyPresenter<EmergencyPresenter.EmergencyView> provideEmergencyPresenter(GetEmergencyContactsUseCase useCase) {
        return new EmergencyPresenterImp<>(useCase);
    }

    @Provides
    @PerActivity
    GetEmergencyContactsUseCase provideEmergencyUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ContactsRepository repository) {
        return new GetEmergencyContactsUseCase(subscribeOn, observeOn, repository);
    }
}
