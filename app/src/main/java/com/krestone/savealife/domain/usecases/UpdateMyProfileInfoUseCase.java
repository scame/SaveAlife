package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class UpdateMyProfileInfoUseCase extends UseCaseCompletable {

    private ProfileRepository profileRepository;

    private UpdateMyProfileInfoRequest updateMyProfileInfoRequest;

    public UpdateMyProfileInfoUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                      ProfileRepository profileRepository) {
        super(subscribeOn, observeOn);
        this.profileRepository = profileRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return profileRepository.updateMyProfileInfo(updateMyProfileInfoRequest);
    }

    public UpdateMyProfileInfoRequest getUpdateMyProfileInfoRequest() {
        return updateMyProfileInfoRequest;
    }

    public void setUpdateMyProfileInfoRequest(UpdateMyProfileInfoRequest updateMyProfileInfoRequest) {
        this.updateMyProfileInfoRequest = updateMyProfileInfoRequest;
    }
}
