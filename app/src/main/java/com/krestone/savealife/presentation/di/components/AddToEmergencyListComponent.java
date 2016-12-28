package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.AddToEmergencyListModule;
import com.krestone.savealife.presentation.fragments.AddToEmergencyListFragment;

import dagger.Subcomponent;

@Subcomponent(modules = AddToEmergencyListModule.class)
@PerActivity
public interface AddToEmergencyListComponent {

    void inject(AddToEmergencyListFragment addToEmergencyListFragment);
}
