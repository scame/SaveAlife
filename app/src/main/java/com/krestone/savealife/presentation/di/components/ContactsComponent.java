package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.presentation.di.PerActivity;
import com.krestone.savealife.presentation.di.modules.ContactsModule;
import com.krestone.savealife.presentation.fragments.ContactsFragment;

import dagger.Subcomponent;

@Subcomponent(modules = ContactsModule.class)
@PerActivity
public interface ContactsComponent {

    void inject(ContactsFragment contactsFragment);
}
