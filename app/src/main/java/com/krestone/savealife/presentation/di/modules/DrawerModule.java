package com.krestone.savealife.presentation.di.modules;

import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.entry.CheckLoginUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.DrawerActivityPresenter;
import com.krestone.savealife.presentation.presenters.DrawerActivityPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class DrawerModule {

    @Provides
    @PerActivity
    DrawerActivityPresenter<DrawerActivityPresenter.DrawerView> provideDashboardPresenter(CheckLoginUseCase checkLoginUseCase) {
        return new DrawerActivityPresenterImpl<>(checkLoginUseCase);
    }

    @Provides
    @PerActivity
    CheckLoginUseCase provideCheckLoginUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository repository) {
        return new CheckLoginUseCase(subscribeOn, observeOn, repository);
    }
}
