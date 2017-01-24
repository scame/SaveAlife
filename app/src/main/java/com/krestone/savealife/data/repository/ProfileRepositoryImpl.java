package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.data.sync.SyncService;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.util.PrefsUtil;

import java.util.concurrent.TimeUnit;

import rx.Completable;
import rx.Observable;
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
        return serverApi.getSomeoneProfileInfo(PrefsUtil.getAuthToken(context), phoneNumber).toSingle()
                .doOnError(throwable -> SyncService.requestSync(SyncType.CONTACTS, context))
                .retryWhen(errors -> errors.zipWith(Observable.range(1, 3), (n, i) -> i)
                        .flatMap(retryCount -> Observable.timer((long) Math.pow(1.25, retryCount), TimeUnit.SECONDS)));
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
        return serverApi.getMyProfileInfo(PrefsUtil.getAuthToken(context)).toSingle();
    }

    @Override
    public Completable updateMyProfileInfo() {
        MyProfileInfoEntity profileInfo = getMyProfileInfoLocal().toBlocking().value();
        UpdateMyProfileInfoRequest request = new UpdateMyProfileInfoRequest(profileInfo);

        return serverApi.updateMyProfileInfo(PrefsUtil.getAuthToken(context), request).toCompletable();
    }
}
