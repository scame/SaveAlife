package com.krestone.savealife.presentation.di.modules;


import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.VerificationUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.VerificationPresenter;
import com.krestone.savealife.presentation.presenters.VerificationPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class VerificationModule {

    @Provides
    @PerActivity
    VerificationPresenter<VerificationPresenter.VerificationView> provideVerificationPresenter(VerificationUseCase useCase) {
        return new VerificationPresenterImpl<>(useCase);
    }

    @Provides
    @PerActivity
    VerificationUseCase provideVerificationUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   EntryRepository entryRepository) {
        return new VerificationUseCase(subscribeOn, observeOn, entryRepository);
    }
}
