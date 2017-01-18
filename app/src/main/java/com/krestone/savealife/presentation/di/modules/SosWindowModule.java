package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.MapObjectsRepository;
import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.map.HelpUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.map.SosWindowPresenter;
import com.krestone.savealife.presentation.presenters.map.SosWindowPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SosWindowModule {

    @Provides
    @PerActivity
    SosWindowPresenter<SosWindowPresenter.SosWindowView> provideSosWindowPresenter(HelpUseCase helpUseCase) {
        return new SosWindowPresenterImpl<>(helpUseCase);
    }

    @Provides
    @PerActivity
    HelpUseCase provideHelpUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                   MapObjectsRepository mapObjectsRepository,
                                   MapboxRepository mapboxRepository) {
        return new HelpUseCase(subscribeOn, observeOn, mapObjectsRepository, mapboxRepository);
    }
}
