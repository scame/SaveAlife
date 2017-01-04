package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.MyProfileModule;
import com.krestone.savealife.presentation.fragments.MyProfileFragment;

import dagger.Subcomponent;

@Subcomponent(modules = MyProfileModule.class)
@PerActivity
public interface MyProfileComponent {

    void inject(MyProfileFragment myProfileFragment);
}
