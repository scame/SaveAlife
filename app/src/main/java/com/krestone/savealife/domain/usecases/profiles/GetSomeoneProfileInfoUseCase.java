package com.krestone.savealife.domain.usecases.profiles;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetSomeoneProfileInfoUseCase extends UseCaseSingle<SomeoneProfileEntity> {

    private ProfileRepository profileRepository;

    private String phoneNumber;

    public GetSomeoneProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                        ProfileRepository profileRepository) {
        super(subscribeOn, observeOn);
        this.profileRepository = profileRepository;
    }

    @Override
    protected Single<SomeoneProfileEntity> getUseCaseSingle() {
        return profileRepository.getSomeoneProfileInfo(phoneNumber);
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
