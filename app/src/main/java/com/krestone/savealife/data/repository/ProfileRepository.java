package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;

import rx.Completable;
import rx.Single;

public interface ProfileRepository {

    Single<SomeoneProfileEntity> getSomeoneProfileInfo(String phoneNumber);

    Single<MyProfileInfoEntity> getMyProfileInfo();

    Completable updateMyProfileInfo(UpdateMyProfileInfoRequest updateMyProfileInfoRequest);
}
