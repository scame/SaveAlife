package com.krestone.savealife.data.repository;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.krestone.savealife.data.rest.ServerApi;
import com.krestone.savealife.util.PrefsUtil;

import rx.Completable;

public class TokensRepositoryImpl implements TokensRepository {

    private ServerApi serverApi;

    private Context context;

    public TokensRepositoryImpl(ServerApi serverApi, Context context) {
        this.serverApi = serverApi;
        this.context = context;
    }

    @Override
    public Completable handleNewFirebaseToken(String firebaseToken) {
        if (needsRefresh()) {
            return serverApi.updateFirebaseToken(PrefsUtil.getAuthToken(context), firebaseToken)
                    .doOnNext(responseBody -> cacheNewToken(firebaseToken))
                    .toCompletable();
        }
        return Completable.complete();
    }

    private boolean needsRefresh() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String oldFbToken = prefs.getString(context.getString(R.string.firebase_token), "");
        return !oldFbToken.equals("");
    }

    private Completable cacheNewToken(String firebaseToken) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(context.getString(R.string.firebase_token), firebaseToken).apply();
        return Completable.complete();
    }
}
