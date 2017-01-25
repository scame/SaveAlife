package com.krestone.savealife.presentation.di.modules;

import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.messages.StartSosUseCase;
import com.krestone.savealife.domain.usecases.messages.StopSosUseCase;
import com.krestone.savealife.domain.usecases.profiles.GetMyProfileInfoUseCase;
import com.krestone.savealife.domain.usecases.profiles.UpdateMyProfileInfoUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.DashboardPresenter;
import com.krestone.savealife.presentation.presenters.DashboardPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DashboardModule {

    @Provides
    @PerActivity
    DashboardPresenter<DashboardPresenter.DashboardView> provideDashboardPresenter(StartSosUseCase startSosUseCase,
                                                                                   StopSosUseCase stopSosUseCase,
                                                                                   GetMyProfileInfoUseCase getMyProfileInfoUseCase,
                                                                                   UpdateMyProfileInfoUseCase updateMyProfileInfoUseCase) {
        return new DashboardPresenterImpl<>(startSosUseCase, stopSosUseCase,
                getMyProfileInfoUseCase, updateMyProfileInfoUseCase);
    }

    @Provides
    @PerActivity
    GetMyProfileInfoUseCase provideGetMyProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ProfileRepository profileRepository) {
        return new GetMyProfileInfoUseCase(subscribeOn, observeOn, profileRepository);
    }

    @Provides
    @PerActivity
    UpdateMyProfileInfoUseCase provideUpdateMyProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                                 ProfileRepository profileRepository) {
        return new UpdateMyProfileInfoUseCase(subscribeOn, observeOn, profileRepository);
    }

    @Provides
    @PerActivity
    StartSosUseCase provideStartSosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                           MessagesRepository messagesRepository) {
        return new StartSosUseCase(subscribeOn, observeOn, messagesRepository);
    }

    @Provides
    @PerActivity
    StopSosUseCase provideStopSosUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                         MessagesRepository messagesRepository) {
        return new StopSosUseCase(subscribeOn, observeOn, messagesRepository);
    }
}
