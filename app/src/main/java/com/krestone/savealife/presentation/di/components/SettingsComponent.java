package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.SettingsModule;
import com.krestone.savealife.presentation.fragments.SettingsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SettingsModule.class)
@PerActivity
public interface SettingsComponent {

    void inject(SettingsFragment settingsFragment);
}
