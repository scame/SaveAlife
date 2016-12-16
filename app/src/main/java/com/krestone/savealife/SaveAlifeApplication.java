package com.krestone.savealife;


import android.app.Application;
import android.content.Intent;

import com.krestone.savealife.data.di.DataModule;
import com.krestone.savealife.data.websockets.SocketTest;
import com.krestone.savealife.presentation.di.components.ApplicationComponent;
import com.krestone.savealife.presentation.di.components.DaggerApplicationComponent;
import com.krestone.savealife.presentation.di.modules.ApplicationModule;
import com.mapbox.mapboxsdk.MapboxAccountManager;

public class SaveAlifeApplication extends Application {

    private static ApplicationComponent applicationComponent;

    private SocketTest socketTest;

    @Override
    public void onCreate() {
        super.onCreate();
        socketTest = new SocketTest();
        MapboxAccountManager.start(this, getString(R.string.default_access_token));
        startService(new Intent(this, LocationService.class));
        buildAppComponent();
    }

    private void buildAppComponent() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .dataModule(new DataModule())
                .build();
    }

    public static ApplicationComponent getAppComponent() {
        return applicationComponent;
    }

    public SocketTest getSocketTest() {
        return socketTest;
    }
}
