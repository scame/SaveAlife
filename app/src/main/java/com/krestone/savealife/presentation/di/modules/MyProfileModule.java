package com.krestone.savealife.presentation.di.modules;

import com.krestone.savealife.data.repository.EntryRepository;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.entry.SignOutUseCase;
import com.krestone.savealife.domain.usecases.profiles.GetMyProfileInfoUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.MyProfilePresenter;
import com.krestone.savealife.presentation.presenters.MyProfilePresenterImpl;

import dagger.Module;
import dagger.Provides;

@Module
public class MyProfileModule {

    @Provides
    @PerActivity
    MyProfilePresenter<MyProfilePresenter.MyProfileView> provideMyProfilePresenter(SignOutUseCase signOutUseCase,
                                                                                   GetMyProfileInfoUseCase profileInfoUseCase) {
        return new MyProfilePresenterImpl<>(profileInfoUseCase, signOutUseCase);
    }

    @Provides
    @PerActivity
    SignOutUseCase provideSignOutUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, EntryRepository entryRepository) {
        return new SignOutUseCase(subscribeOn, observeOn, entryRepository);
    }

    @Provides
    @PerActivity
    GetMyProfileInfoUseCase provideGetMyProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                           ProfileRepository profileRepository) {
        return new GetMyProfileInfoUseCase(subscribeOn, observeOn, profileRepository);
    }
}
