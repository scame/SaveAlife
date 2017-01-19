package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.repository.ProfileRepository;
import com.krestone.savealife.data.sync.events.SyncType;

import rx.Completable;

public class ProfileSync extends AbstractSync {

    private ProfileRepository profileRepository;

    public ProfileSync(Context context, ProfileRepository profileRepository) {
        super(context);
        this.profileRepository = profileRepository;
    }

    @Override
    protected SyncType getSyncType() {
        return SyncType.PROFILE;
    }

    @Override
    protected Completable post() {
        Throwable throwable = profileRepository.updateMyProfileInfo().get();
        return completeWithErrorCheck(throwable);
    }

    @Override
    protected Completable get() {
        MyProfileInfoEntity profileInfo = profileRepository.getMyProfileInfo().toBlocking().value();
        Throwable throwable = profileRepository
                .updateMyProfileInfoLocal(new UpdateMyProfileInfoRequest(profileInfo)).get();
        return completeWithErrorCheck(throwable);
    }
}
