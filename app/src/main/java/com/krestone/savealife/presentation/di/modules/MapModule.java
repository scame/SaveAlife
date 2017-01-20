package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.data.repository.MapObjectsRepository;
import com.krestone.savealife.data.repository.MapboxRepository;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.GetMapObjectsUseCase;
import com.krestone.savealife.domain.usecases.LastKnownLocationUseCase;
import com.krestone.savealife.domain.usecases.LocationUpdatesUseCase;
import com.krestone.savealife.domain.usecases.map.GetHumanReadableAddressUseCase;
import com.krestone.savealife.domain.usecases.map.GetRouteUseCase;
import com.krestone.savealife.domain.usecases.messages.StopHelpUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.map.MapPresenter;
import com.krestone.savealife.presentation.presenters.map.MapPresenterImp;

import dagger.Module;
import dagger.Provides;

@Module
public class MapModule {

    @Provides
    @PerActivity
    MapPresenter<MapPresenter.MapView> provideMapPresenter(LastKnownLocationUseCase lastKnownLocationUseCase,
                                                           LocationUpdatesUseCase locationUpdatesUseCase,
                                                           GetMapObjectsUseCase mapObjectsUseCase,
                                                           GetHumanReadableAddressUseCase addressUseCase,
                                                           GetRouteUseCase getRouteUseCase,
                                                           StopHelpUseCase stopHelpUseCase) {
        return new MapPresenterImp<>(lastKnownLocationUseCase, locationUpdatesUseCase, mapObjectsUseCase,
                addressUseCase, getRouteUseCase, stopHelpUseCase);
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

    @Provides
    @PerActivity
    GetHumanReadableAddressUseCase provideHumanReadableAddressUseCase(SubscribeOn subscribeOn,
                                                                      ObserveOn observeOn,
                                                                      MapboxRepository repository) {
        return new GetHumanReadableAddressUseCase(subscribeOn, observeOn, repository);
    }

    @Provides
    @PerActivity
    GetRouteUseCase provideGetRouteUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                           MapboxRepository mapboxRepository) {
        return new GetRouteUseCase(subscribeOn, observeOn, mapboxRepository);
    }

    @Provides
    @PerActivity
    StopHelpUseCase provideStopHelpingUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                              MessagesRepository messagesRepository) {
        return new StopHelpUseCase(subscribeOn, observeOn, messagesRepository);
    }
}
