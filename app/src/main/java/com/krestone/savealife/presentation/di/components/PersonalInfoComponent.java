package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.PersonalInfoModule;
import com.krestone.savealife.presentation.fragments.entry.PersonalInfoFragment;

import dagger.Subcomponent;

@PerActivity
@Subcomponent(modules = PersonalInfoModule.class)
public interface PersonalInfoComponent {

    void inject(PersonalInfoFragment fragment);
}
