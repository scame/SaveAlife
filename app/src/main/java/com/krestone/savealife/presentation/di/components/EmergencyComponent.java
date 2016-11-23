package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.EmergencyModule;
import com.krestone.savealife.presentation.fragments.EmergencyContactsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = EmergencyModule.class)
@PerActivity
public interface EmergencyComponent {

    void inject(EmergencyContactsFragment fragment);
}
