package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.SettingsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.settings.ChangeLocationUpdatesUseCase;
import com.krestone.savealife.domain.usecases.settings.ChangeMessagesStateUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.SettingsPresenter;
import com.krestone.savealife.presentation.presenters.SettingsPresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.SettingsPresenter.SettingsView;

@Module
public class SettingsModule {

    @Provides
    @PerActivity
    SettingsPresenter<SettingsView> provideSettingsPresenter(ChangeLocationUpdatesUseCase locationUseCase,
                                                             ChangeMessagesStateUseCase messagesUseCase) {
        return new SettingsPresenterImpl<>(locationUseCase, messagesUseCase);
    }

    @Provides
    @PerActivity
    ChangeLocationUpdatesUseCase provideLocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                               SettingsRepository settingsRepository) {
        return new ChangeLocationUpdatesUseCase(subscribeOn, observeOn, settingsRepository);
    }

    @Provides
    @PerActivity
    ChangeMessagesStateUseCase provideMessagesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                      SettingsRepository settingsRepository) {
        return new ChangeMessagesStateUseCase(subscribeOn, observeOn, settingsRepository);
    }
}
