package com.krestone.savealife.presentation.di.components;


import com.krestone.savealife.LocationService;
import com.krestone.savealife.data.di.DataModule;
import com.krestone.savealife.data.di.NetworkingModule;
import com.krestone.savealife.data.di.SyncManagerComponent;
import com.krestone.savealife.data.di.SyncManagerModule;
import com.krestone.savealife.data.messages.MyFirebaseInstanceIDService;
import com.krestone.savealife.data.messages.MyFirebaseMessagingService;
import com.krestone.savealife.presentation.di.modules.AddToEmergencyListModule;
import com.krestone.savealife.presentation.di.modules.ApplicationModule;
import com.krestone.savealife.presentation.di.modules.ContactProfileModule;
import com.krestone.savealife.presentation.di.modules.DashboardModule;
import com.krestone.savealife.presentation.di.modules.DrawerModule;
import com.krestone.savealife.presentation.di.modules.EmergencyModule;
import com.krestone.savealife.presentation.di.modules.MapModule;
import com.krestone.savealife.presentation.di.modules.MyProfileModule;
import com.krestone.savealife.presentation.di.modules.NotificationsModule;
import com.krestone.savealife.presentation.di.modules.PersonalInfoModule;
import com.krestone.savealife.presentation.di.modules.RegistrationNumberModule;
import com.krestone.savealife.presentation.di.modules.SettingsModule;
import com.krestone.savealife.presentation.di.modules.SignInModule;
import com.krestone.savealife.presentation.di.modules.SosWindowModule;
import com.krestone.savealife.presentation.di.modules.VerificationModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ApplicationModule.class, DataModule.class, NetworkingModule.class})
public interface ApplicationComponent {

    void inject(LocationService locationService);

    void inject(MyFirebaseMessagingService myFirebaseMessagingService);

    void inject(MyFirebaseInstanceIDService myFirebaseInstanceIDService);

    MapComponent provideMapComponent(MapModule mapModule);

    AddToEmergencyListComponent provideAddToEmergencyListComponent(AddToEmergencyListModule addToEmergencyListModule);

    EmergencyComponent provideEmergencyComponent(EmergencyModule emergencyModule);

    RegistrationNumberComponent provideRegistrationNumberComponent(RegistrationNumberModule module);

    VerificationComponent provideVerificationComponent(VerificationModule verificationModule);

    PersonalInfoComponent providePersonalInfoComponent(PersonalInfoModule personalInfoModule);

    DrawerComponent provideDrawerComponent(DrawerModule drawerModule);

    MyProfileComponent provideMyProfileComponent(MyProfileModule myProfileModule);

    SignInComponent provideSignInComponent(SignInModule signInModule);

    SyncManagerComponent provideSyncManagerComponent(SyncManagerModule syncManagerModule);

    ContactProfileComponent provideContactProfileComponent(ContactProfileModule profileModule);

    SosWindowComponent provideSosWindowComponent(SosWindowModule sosWindowModule);

    DashboardComponent provideDashboardComponent(DashboardModule dashboardModule);

    NotificationsComponent provideNotificationsComponent(NotificationsModule notificationsModule);

    SettingsComponent provideSettingsComponent(SettingsModule settingsModule);
}
