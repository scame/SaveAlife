package com.krestone.savealife.presentation.di.components;

import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.DashboardModule;
import com.krestone.savealife.presentation.fragments.DashboardFragment;

import dagger.Subcomponent;

@Subcomponent(modules = DashboardModule.class)
@PerActivity
public interface DashboardComponent {

    void inject(DashboardFragment dashboardFragment);
}
