package com.krestone.savealife.presentation.di.modules;

import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.profiles.GetSomeoneProfileInfoUseCase;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.presenters.contacts.ContactProfilePresenter;
import com.krestone.savealife.presentation.presenters.contacts.ContactProfilePresenterImpl;

import dagger.Module;
import dagger.Provides;

import static com.krestone.savealife.presentation.presenters.contacts.ContactProfilePresenter.ContactProfileView;

@Module
public class ContactProfileModule {

    @Provides
    @PerActivity
    ContactProfilePresenter<ContactProfileView> provideContactProfilePresenter(
            GetSomeoneProfileInfoUseCase profileInfoUseCase) {
        return new ContactProfilePresenterImpl<>(profileInfoUseCase);
    }

    @Provides
    @PerActivity
    GetSomeoneProfileInfoUseCase provideGetSomeoneProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                                                     ProfileRepository profileRepository) {
        return new GetSomeoneProfileInfoUseCase(subscribeOn, observeOn, profileRepository);
    }
}
