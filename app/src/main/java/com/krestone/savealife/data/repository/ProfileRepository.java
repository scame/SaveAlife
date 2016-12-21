package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;

import rx.Single;

public interface ProfileRepository {

    Single<SomeoneProfileEntity> getSomeoneProfileInfo(String phoneNumber);
}
