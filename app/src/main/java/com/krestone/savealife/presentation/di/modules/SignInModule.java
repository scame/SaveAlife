package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.entry.SignInUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.entry.SignInPresenter;
import com.krestone.savealife.presentation.presenters.entry.SignInPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class SignInModule {

    @Provides
    @PerActivity
    SignInPresenter<SignInPresenter.SignInView> provideSignInPresenter(SignInUseCase signInUseCase) {
        return new SignInPresenterImpl<>(signInUseCase);
    }

    @Provides
    @PerActivity
    SignInUseCase provideSignInUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository entryRepository) {
        return new SignInUseCase(subscribeOn, observeOn, entryRepository);
    }
}
