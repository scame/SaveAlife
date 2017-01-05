package com.krestone.savealife.data.repository;


import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.presentation.models.UserModel;

import okhttp3.ResponseBody;
import rx.Completable;
import rx.Single;

public interface EntryRepository {

    Single<ResponseBody> sendPhoneNumber(String phoneNumber);

    Single<ResponseBody> sendVerificationCode(String phoneNumber, String code);

    Completable sendPersonalInfo(PersonalInfoHolder personalInfoHolder);

    Single<String> getAuthToken(String password, String phoneNumber);

    Single<Boolean> getLoginStatus();

    Completable signOut();

    Single<UserModel> getLastLoggedInUserInfo();

    Single<Boolean> signIn(String password);
}
