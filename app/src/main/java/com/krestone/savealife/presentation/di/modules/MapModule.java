package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.data.repository.MapObjectsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.GetMapObjectsUseCase;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.domain.usecases.LocationUpdatesUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.MapPresenter;
import com.krestone.savealife.presentation.presenters.MapPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @PerActivity
    MapPresenter<MapPresenter.MapView> provideMapPresenter(LastKnownLocationUseCase lastKnownLocationUseCase,
                                                           LocationUpdatesUseCase locationUpdatesUseCase,
                                                           GetMapObjectsUseCase mapObjectsUseCase) {
        return new MapPresenterImp<>(lastKnownLocationUseCase, locationUpdatesUseCase, mapObjectsUseCase);
    }

    @Provides
    @PerActivity
    LastKnownLocationUseCase provideLastKnownLocationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                             LocationRepository locationRepository) {
        return new LastKnownLocationUseCase(subscribeOn, observeOn, locationRepository);
    }

    @Provides
    @PerActivity
    LocationUpdatesUseCase provideLocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                         LocationRepository locationRepository) {
        return new LocationUpdatesUseCase(subscribeOn, observeOn, locationRepository);
    }

    @Provides
    @PerActivity
    GetMapObjectsUseCase provideMapObjectsUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                  MapObjectsRepository mapRepository) {
        return new GetMapObjectsUseCase(subscribeOn, observeOn, mapRepository);
    }
}
