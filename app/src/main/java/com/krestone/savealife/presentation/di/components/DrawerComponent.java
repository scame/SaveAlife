package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.DrawerModule;

import dagger.Subcomponent;

@Subcomponent(modules = DrawerModule.class)
@PerActivity
public interface DrawerComponent {

    void inject(DrawerActivity drawerActivity);
}
