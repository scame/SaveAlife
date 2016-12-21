package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.rest.ServerApi;

import rx.Single;

public class ProfileRepositoryImpl implements ProfileRepository {

    private ServerApi serverApi;

    public ProfileRepositoryImpl(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    @Override
    public Single<SomeoneProfileEntity> getSomeoneProfileInfo(String phoneNumber) {
        return serverApi.getSomeoneProfileInfo(phoneNumber).toSingle();
    }
}
