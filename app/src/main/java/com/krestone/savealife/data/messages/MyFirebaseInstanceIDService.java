package com.krestone.savealife.data.messages;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.repository.TokensRepository;

import javax.inject.Inject;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Inject
    TokensRepository tokensRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveAlifeApplication.getAppComponent().inject(this);
    }

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.i(TAG, "Refreshed token: " + refreshedToken);

        tokensRepository.handleNewFirebaseToken(refreshedToken).subscribe(
                        () -> Log.i("onxRefresh", "ok"),
                        throwable -> Log.i("onxRefreshErr", throwable.getLocalizedMessage())
                );
    }
}
