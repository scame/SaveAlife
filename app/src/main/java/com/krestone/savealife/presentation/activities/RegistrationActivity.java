package com.krestone.savealife.presentation.activities;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.presentation.di.components.PersonalInfoComponent;
import com.krestone.savealife.presentation.di.components.RegistrationNumberComponent;
import com.krestone.savealife.presentation.di.components.VerificationComponent;
import com.krestone.savealife.presentation.di.modules.PersonalInfoModule;
import com.krestone.savealife.presentation.di.modules.RegistrationNumberModule;
import com.krestone.savealife.presentation.di.modules.VerificationModule;
import com.krestone.savealife.presentation.fragments.registration.PersonalInfoFragment;
import com.krestone.savealife.presentation.fragments.registration.PhoneNumberFragment;
import com.krestone.savealife.presentation.fragments.registration.VerificationFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity implements
        PersonalInfoFragment.PersonalInfoListener, PhoneNumberFragment.PhoneNumberListener,
        VerificationFragment.VerificationListener {

    private static final String PERSONAL_INFO_FRAGM = "personalInfoFragm";
    private static final String PHONE_NUMBER_FRAGM = "phoneNumberFragm";
    private static final String VERIFICATION_FRAGM = "verificationFragm";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private RegistrationNumberComponent registrationNumberComponent;

    private VerificationComponent verificationComponent;

    private PersonalInfoComponent personalInfoComponent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        ButterKnife.bind(this);

        configureToolbar();
        configureDefaultFragment();
    }

    private void configureDefaultFragment() {
        if (getSupportFragmentManager().getFragments() == null) {
            replaceFragment(PHONE_NUMBER_FRAGM, new PhoneNumberFragment());
        }
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        });
    }

    @Override
    public void onPhoneNumberContinue(String phoneNumber) {
        replaceFragmentWithBackstack(VERIFICATION_FRAGM, VerificationFragment.newInstance(phoneNumber));
    }

    @Override
    public void onVerificationClick(String phoneNumber, String verificationCode) {
        replaceFragmentWithBackstack(PERSONAL_INFO_FRAGM, PersonalInfoFragment.newInstance(phoneNumber, verificationCode));
    }

    @Override
    public void onSignUpClick() {
        startActivity(new Intent(this, DrawerActivity.class));
    }

    private void replaceFragment(String tag, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.registration_activity_fl, fragment, tag)
                .commit();
    }

    private void replaceFragmentWithBackstack(String tag, Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.registration_activity_fl, fragment, tag)
                .addToBackStack(null)
                .commit();
    }

    public RegistrationNumberComponent provideRegistrationNumberComponent() {
        if (registrationNumberComponent == null) {
            registrationNumberComponent = SaveAlifeApplication.getAppComponent()
                    .provideRegistrationNumberComponent(new RegistrationNumberModule());
        }
        return registrationNumberComponent;
    }

    public VerificationComponent provideVerificationComponent() {
        if (verificationComponent == null) {
            verificationComponent = SaveAlifeApplication.getAppComponent()
                    .provideVerificationComponent(new VerificationModule());
        }
        return verificationComponent;
    }

    public PersonalInfoComponent providePersonalInfoComponent() {
        if (personalInfoComponent == null) {
            personalInfoComponent = SaveAlifeApplication.getAppComponent()
                    .providePersonalInfoComponent(new PersonalInfoModule());
        }
        return personalInfoComponent;
    }
}
