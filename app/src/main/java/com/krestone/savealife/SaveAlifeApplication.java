package com.krestone.savealife;


import android.app.Application;

import com.krestone.savealife.data.di.DataModule;
import com.krestone.savealife.presentation.di.components.ApplicationComponent;
import com.krestone.savealife.presentation.di.components.DaggerApplicationComponent;
import com.krestone.savealife.presentation.di.modules.ApplicationModule;
import com.mapbox.mapboxsdk.MapboxAccountManager;

public class SaveAlifeApplication extends Application {

    private static ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        MapboxAccountManager.start(this, getString(R.string.default_access_token));
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
}
