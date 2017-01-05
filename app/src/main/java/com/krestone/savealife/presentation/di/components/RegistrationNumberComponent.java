package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.RegistrationNumberModule;
import com.krestone.savealife.presentation.fragments.entry.PhoneNumberFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = RegistrationNumberModule.class)
public interface RegistrationNumberComponent {

    void inject(PhoneNumberFragment phoneNumberFragment);
}
