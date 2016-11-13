package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.MapModule;
import com.krestone.savealife.presentation.fragments.MapFragment;

import dagger.Subcomponent;


@PerActivity
@Subcomponent(modules = MapModule.class)
public interface MapComponent {

    void inject(MapFragment mapFragment);
}
