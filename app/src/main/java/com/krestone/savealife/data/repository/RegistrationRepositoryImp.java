package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.rest.ServerApi;

import okhttp3.ResponseBody;
import rx.Single;

public class RegistrationRepositoryImp implements RegistrationRepository {

    private ServerApi serverApi;

    public RegistrationRepositoryImp(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    @Override
    public Single<ResponseBody> sendPhoneNumber(String phoneNumber) {
        return null;
    }

    @Override
    public Single<ResponseBody> sendVerificationCode(String phoneNumber, String code) {
        return null;
    }

    @Override
    public Single<ResponseBody> sendPersonalInfo(PersonalInfoHolder personalInfoHolder) {
        return null;
    }
}
