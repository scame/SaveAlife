package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;

import okhttp3.ResponseBody;
import rx.Single;

public interface RegistrationRepository {

    Single<ResponseBody> sendPhoneNumber(String phoneNumber);

    Single<ResponseBody> sendVerificationCode(String phoneNumber, String code);

    Single<ResponseBody> sendPersonalInfo(PersonalInfoHolder personalInfoHolder);
}
