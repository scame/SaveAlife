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
        return serverApi.getSomeoneProfileInfo(getAuthToken(), phoneNumber).toSingle();
    }

    @Override
    public Single<MyProfileInfoEntity> getMyProfileInfoLocal() {
        MyProfileInfoEntity profileInfo = new MyProfileInfoEntity();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        profileInfo.setFirstName(sharedPrefs.getString(context.getString(R.string.firstName), ""));
        profileInfo.setLastName(sharedPrefs.getString(context.getString(R.string.lastName), ""));
        profileInfo.setMedicalQualification(sharedPrefs.getString(context.getString(R.string.med_qualification), ""));
        profileInfo.setRole(sharedPrefs.getString(context.getString(R.string.profile_role), ""));

        return Single.just(profileInfo);
    }

    @Override
    public Completable updateMyProfileInfoLocal(UpdateMyProfileInfoRequest profileInfo) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(context.getString(R.string.firstName), profileInfo.getFirstName()).apply();
        editor.putString(context.getString(R.string.lastName), profileInfo.getLastName()).apply();
        editor.putString(context.getString(R.string.med_qualification), profileInfo.getMedicalQualification()).apply();
        editor.putString(context.getString(R.string.profile_role), profileInfo.getRole()).apply();

        return Completable.complete();
    }

    @Override
    public Single<MyProfileInfoEntity> getMyProfileInfo() {
        return serverApi.getMyProfileInfo(getAuthToken()).toSingle();
    }

    @Override
    public Completable updateMyProfileInfo() {
        MyProfileInfoEntity profileInfo = getMyProfileInfoLocal().toBlocking().value();
        UpdateMyProfileInfoRequest request = new UpdateMyProfileInfoRequest(profileInfo);

        return serverApi.updateMyProfileInfo(getAuthToken(), request).toCompletable();
    }

    private String getAuthToken() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(context.getString(R.string.auth_token), "");
    }
}
