package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseSingle;

import rx.Single;

public class GetMyProfileInfoUseCase extends UseCaseSingle<MyProfileInfoEntity> {

    private ProfileRepository profileRepository;

    public GetMyProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn, ProfileRepository profileRepository) {
        super(subscribeOn, observeOn);
        this.profileRepository = profileRepository;
    }

    @Override
    protected Single<MyProfileInfoEntity> getUseCaseSingle() {
        return profileRepository.getMyProfileInfo();
    }
}
