package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.SignInModule;
import com.krestone.savealife.presentation.fragments.entry.SignInFragment;

import dagger.Subcomponent;

@Subcomponent(modules = SignInModule.class)
@PerActivity
public interface SignInComponent {

    void inject(SignInFragment signInFragment);
}
