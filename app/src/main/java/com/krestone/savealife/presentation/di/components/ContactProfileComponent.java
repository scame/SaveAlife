package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.ContactProfileModule;
import com.krestone.savealife.presentation.fragments.contacts.ContactProfileFragment;

import dagger.Subcomponent;

@Subcomponent(modules = ContactProfileModule.class)
@PerActivity
public interface ContactProfileComponent {

    void inject(ContactProfileFragment fragment);
}
