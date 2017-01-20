package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.messages.StartHelpUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.map.SosWindowPresenter;
import com.krestone.savealife.presentation.presenters.map.SosWindowPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SosWindowModule {

    @Provides
    @PerActivity
    SosWindowPresenter<SosWindowPresenter.SosWindowView> provideSosWindowPresenter(StartHelpUseCase startHelpUseCase) {
        return new SosWindowPresenterImpl<>(startHelpUseCase);
    }

    @Provides
    @PerActivity
    StartHelpUseCase provideHelpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                        MessagesRepository messagesRepository,
                                        MapboxRepository mapboxRepository) {
        return new StartHelpUseCase(subscribeOn, observeOn, mapboxRepository, messagesRepository);
    }
}
