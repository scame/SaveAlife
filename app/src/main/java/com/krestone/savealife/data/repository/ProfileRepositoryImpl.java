package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.rest.ServerApi;

import rx.Completable;
import rx.Single;

public class ProfileRepositoryImpl implements ProfileRepository {

    private ServerApi serverApi;

    private Context context;

    public ProfileRepositoryImpl(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
    }

    @Override
    public Single<SomeoneProfileEntity> getSomeoneProfileInfo(String phoneNumber) {
        return serverApi.getSomeoneProfileInfo(phoneNumber, getAuthToken()).toSingle();
    }

    @Override
    public Single<MyProfileInfoEntity> getMyProfileInfo() {
        return serverApi.getMyProfileInfo(getAuthToken()).toSingle();
    }

    @Override
    public Completable updateMyProfileInfo(UpdateMyProfileInfoRequest updateMyProfileInfoRequest) {
        return serverApi.updateMyProfileInfo(getAuthToken(), updateMyProfileInfoRequest).toCompletable();
    }

    private String getAuthToken() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(context.getString(R.string.auth_token), "");
    }
}
