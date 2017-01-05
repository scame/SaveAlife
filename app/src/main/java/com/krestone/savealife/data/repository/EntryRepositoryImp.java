package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Base64;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.presentation.models.UserModel;

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
        return serverApi.getAuthToken(encodeEntryData(password, phoneNumber))
                .map(response -> response.headers().get("x-auth-token"))
                .toSingle();
    }

    private String encodeEntryData(String password, String phoneNumber) {
        String numberAndPasswordStr = phoneNumber + ":" + password;
        return "Basic " + Base64.encodeToString(numberAndPasswordStr.getBytes(), Base64.NO_WRAP);
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
    public Completable sendPersonalInfo(PersonalInfoHolder personalInfoHolder) {
        return serverApi.sendPersonalInfo(personalInfoHolder).toSingle()
                .flatMap(responseBody -> getAuthToken(personalInfoHolder.getPassword(), personalInfoHolder.getPhoneNumber()))
                .map(this::cacheAuthToken)
                .map(ignore -> cacheEntryInfo(personalInfoHolder))
                .toCompletable();
    }

    private Completable cacheAuthToken(String token) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(context.getString(R.string.auth_token), token).apply();
        return Completable.complete();
    }


    private Completable cacheEntryInfo(PersonalInfoHolder personalInfoHolder) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(getString(R.string.password), personalInfoHolder.getPassword()).apply();
        editor.putString(getString(R.string.phone_number), personalInfoHolder.getPhoneNumber()).apply();
        editor.putString(getString(R.string.first_name), personalInfoHolder.getFirstName()).apply();
        editor.putString(getString(R.string.last_name), personalInfoHolder.getLastName()).apply();
        editor.putString(getString(R.string.current_token), personalInfoHolder.getCurrentToken()).apply();
        editor.putString(getString(R.string.med_qualification), personalInfoHolder.getMedicalQualification()).apply();
        editor.putBoolean(getString(R.string.isLoggedIn), true);
        return Completable.complete();
    }

    @Override
    public Single<UserModel> getLastLoggedInUserInfo() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        UserModel userModel = new UserModel();

        userModel.setFirstName(sharedPrefs.getString(getString(R.string.first_name), ""));
        userModel.setLastName(sharedPrefs.getString(getString(R.string.last_name), ""));
        userModel.setPassword(sharedPrefs.getString(getString(R.string.password), ""));
        userModel.setPhoneNumber(sharedPrefs.getString(getString(R.string.phone_number), ""));
        userModel.setMedicalQualification(sharedPrefs.getString(getString(R.string.med_qualification), ""));
        // TODO: implement profile img URI fetching

        return Single.just(userModel);
    }

    @Override
    public Single<Boolean> getLoginStatus() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return Single.just(sharedPrefs.getBoolean(getString(R.string.isLoggedIn), false));
    }

    @Override
    public Completable signOut() {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean(getString(R.string.isLoggedIn), false).apply();
        return Completable.complete();
    }

    private String getString(int stringId) {
        return context.getString(stringId);
    }
}
