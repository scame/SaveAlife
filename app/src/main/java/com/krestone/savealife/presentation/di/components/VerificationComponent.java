package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.VerificationModule;
import com.krestone.savealife.presentation.fragments.registration.VerificationFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = VerificationModule.class)
public interface VerificationComponent {

    void inject(VerificationFragment verificationFragment);
}
