package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.SosWindowModule;
import com.krestone.savealife.presentation.fragments.SosWindowFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SosWindowModule.class)
@PerActivity
public interface SosWindowComponent {

    void inject(SosWindowFragment sosWindowFragment);
}
