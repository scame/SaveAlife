package com.krestone.savealife.presentation.di.components;

import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.NotificationsModule;
import com.krestone.savealife.presentation.fragments.NotificationsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = NotificationsModule.class)
@PerActivity
public interface NotificationsComponent {

    void inject(NotificationsFragment notificationsFragment);
}
