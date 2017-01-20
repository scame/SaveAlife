package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.entities.responses.PhoneNumberResponse;
import com.krestone.savealife.data.entities.responses.SomeoneProfileEntity;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Completable;
import rx.Single;


public class EntryRepositoryImp implements EntryRepository {

    private ServerApi serverApi;

    private Context context;

    public EntryRepositoryImp(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
    }

    @Override
    public Single<String> getAuthToken(String password, String phoneNumber) {
        return serverApi.getAuthToken(encodeEntryData(password, phoneNumber), PrefsUtil.getFirebaseToken(context))
                .toSingle()
                .map(response -> {
                    try {
                        return response.errorBody() == null ? response.headers().get("x-auth-token")
                                                            : response.errorBody().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private String encodeEntryData(String password, String phoneNumber) {
        String numberAndPasswordStr = phoneNumber + ":" + password;
        return "Basic " + Base64.encodeToString(numberAndPasswordStr.getBytes(), Base64.NO_WRAP);
    }

    @Override
    public Single<PhoneNumberResponse> sendPhoneNumber(String phoneNumber) {
        return serverApi.sendRegistrationNumber(new PhoneNumberHolder(phoneNumber)).toSingle();
    }

    @Override
    public Single<ResponseBody> sendVerificationCode(String phoneNumber, String token) {
        return serverApi.sendVerificationCode(new VerificationHolder(token, phoneNumber)).toSingle();
    }

    @Override
    public Completable sendPersonalInfo(PersonalInfoHolder holder) {
        return serverApi.sendPersonalInfo(holder).toSingle()
                .flatMap(responseBody -> getAuthToken(holder.getPassword(), holder.getPhoneNumber()))
                .map(this::cacheAuthToken)
                .map(ignore -> cacheEntryInfo(holder.getPassword(), holder.getFirstName(),
                        holder.getLastName(), holder.getPhoneNumber(), holder.getMedicalQualification()))
                .toCompletable();
    }

    private Completable cacheAuthToken(String token) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(context.getString(R.string.auth_token), token).apply();
        return Completable.complete();
    }


    private Completable cacheEntryInfo(String password, String firstName, String lastName,
                                       String phoneNumber, String medicalQualification) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(getString(R.string.password), password).apply();
        editor.putString(getString(R.string.phone_number), phoneNumber).apply();
        editor.putString(getString(R.string.lastName), lastName).apply();
        editor.putString(getString(R.string.firstName), firstName).apply();
        editor.putString(getString(R.string.med_qualification), medicalQualification).apply();
        changeLoginStatus(true);

        return Completable.complete();
    }


    @Override
    public Single<Boolean> getLoginStatus() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Single.just(sharedPrefs.getBoolean(getString(R.string.isLoggedIn), false));
    }


    @Override
    public Single<Boolean> signIn(String password, SomeoneProfileEntity profileEntity) {
        return getAuthToken(password, profileEntity.getPhoneNumber())
                .onErrorReturn(throwable -> "")
                .map(token -> {
                    if (!token.equals("")) {
                        cacheAuthToken(token);
                        cacheEntryInfo(password, profileEntity.getFirstName(), profileEntity.getLastName(),
                                profileEntity.getPhoneNumber(), profileEntity.getMedicalQualification());
                        return true;
                    }
                    return false;
                });
    }

    @Override
    public Completable signOut() {
        return changeLoginStatus(false);
    }

    private Completable changeLoginStatus(boolean isLoggedIn) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(getString(R.string.isLoggedIn), isLoggedIn).apply();
        return Completable.complete();
    }

    private String getString(int stringId) {
        return context.getString(stringId);
    }
}
