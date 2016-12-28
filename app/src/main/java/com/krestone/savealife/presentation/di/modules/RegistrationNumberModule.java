package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.entry.RegistrationNumberUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.RegistrationNumberPresenter;
import com.krestone.savealife.presentation.presenters.RegistrationNumberPresenterImp;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.RegistrationNumberPresenter.RegistrationNumberView;

@Module
public class RegistrationNumberModule {

    @Provides
    @PerActivity
    RegistrationNumberPresenter<RegistrationNumberView> provideRegistrationNumberPresenter(RegistrationNumberUseCase useCase) {
        return new RegistrationNumberPresenterImp<>(useCase);
    }

    @Provides
    @PerActivity
    RegistrationNumberUseCase provideRegistrationNumberUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                               EntryRepository entryRepository) {
        return new RegistrationNumberUseCase(subscribeOn, observeOn, entryRepository);
    }
}
