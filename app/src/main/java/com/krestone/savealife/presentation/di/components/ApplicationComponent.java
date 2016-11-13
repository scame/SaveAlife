package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.data.di.DataModule;
import com.krestone.savealife.presentation.di.modules.ApplicationModule;
import com.krestone.savealife.presentation.di.modules.MapModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class})
public interface ApplicationComponent {

    MapComponent provideMapSubcomponent(MapModule mapModule);
}
