package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.requests.PersonalInfoHolder;
import com.krestone.savealife.data.entities.requests.PhoneNumberHolder;
import com.krestone.savealife.data.entities.requests.VerificationHolder;
import com.krestone.savealife.data.rest.ServerApi;

import okhttp3.ResponseBody;
import rx.Single;

public class RegistrationRepositoryImp implements RegistrationRepository {

    private ServerApi serverApi;

    private Context context;

    public RegistrationRepositoryImp(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
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
        return serverApi.sendPersonalInfo(personalInfoHolder).toSingle()
                .doOnSuccess(responseBody -> cacheEntryInfo(personalInfoHolder));
    }

    private void cacheEntryInfo(PersonalInfoHolder personalInfoHolder) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();

        editor.putString(getString(R.string.password), personalInfoHolder.getPassword()).apply();
        editor.putString(getString(R.string.phone_number), personalInfoHolder.getPhoneNumber()).apply();
        editor.putString(getString(R.string.first_name), personalInfoHolder.getFirstName()).apply();
        editor.putString(getString(R.string.last_name), personalInfoHolder.getLastName()).apply();
        editor.putString(getString(R.string.current_token), personalInfoHolder.getCurrentToken()).apply();
        editor.putString(getString(R.string.med_qualification), personalInfoHolder.getMedicalQualification()).apply();
    }

    private String getString(int stringId) {
        return context.getString(stringId);
    }
}
