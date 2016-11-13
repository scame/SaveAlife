package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.MapPresenter;
import com.krestone.savealife.presentation.presenters.MapPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @PerActivity
    MapPresenter<MapPresenter.MapView> provideMapPresenter(LastKnownLocationUseCase lastKnownLocationUseCase) {
        return new MapPresenterImp<>(lastKnownLocationUseCase);
    }

    @Provides
    @PerActivity
    LastKnownLocationUseCase provideLastKnownLocationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                             LocationRepository locationRepository) {
        return new LastKnownLocationUseCase(subscribeOn, observeOn, locationRepository);
    }
}
