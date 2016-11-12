package com.krestone.savealife;


import android.app.Application;

import com.mapbox.mapboxsdk.MapboxAccountManager;

public class SaveAlifeApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MapboxAccountManager.start(this, getString(R.string.default_access_token));
    }
}
