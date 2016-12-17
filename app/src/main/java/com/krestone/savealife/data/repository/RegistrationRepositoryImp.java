package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
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
        return serverApi.sendRegistrationNumber(new PhoneNumberHolder(phoneNumber)).toSingle();
    }

    @Override
    public Single<ResponseBody> sendVerificationCode(String phoneNumber, String token) {
        return serverApi.sendVerificationCode(new VerificationHolder(token, phoneNumber)).toSingle();
    }

    @Override
    public Single<ResponseBody> sendPersonalInfo(PersonalInfoHolder personalInfoHolder) {
        return serverApi.sendPersonalInfo(personalInfoHolder).toSingle();
    }
}
