package com.krestone.savealife.presentation.di.modules;

import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.entry.PersonalInfoUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.entry.PersonalInfoPresenter;
import com.krestone.savealife.presentation.presenters.entry.PersonalInfoPresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class PersonalInfoModule {

    @Provides
    @PerActivity
    PersonalInfoPresenter<PersonalInfoPresenter.PersonalInfoView> providePersonalInfoPresenter(PersonalInfoUseCase useCase) {
        return new PersonalInfoPresenterImpl<>(useCase);
    }

    @Provides
    @PerActivity
    PersonalInfoUseCase providePersonalInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                   EntryRepository entryRepository) {
        return new PersonalInfoUseCase(subscribeOn, observeOn, entryRepository);
    }
}
