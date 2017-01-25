package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.SwitchPreferenceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.presenters.SettingsPresenter;

import javax.inject.Inject;

public class SettingsFragment extends PreferenceFragmentCompat implements SettingsPresenter.SettingsView {

    @Inject
    SettingsPresenter<SettingsPresenter.SettingsView> settingsPresenter;

    private SwitchPreferenceCompat locationSwitch;

    private SwitchPreferenceCompat notificationsSwitch;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inject();
        configureLocationSwitch();
        configureNotificationsSwitch();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideSettingsComponent().inject(this);
        }
    }

    private void configureLocationSwitch() {
        locationSwitch = (SwitchPreferenceCompat) getPreferenceManager().findPreference("location_prefs_key");
        locationSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean isEnabled = (Boolean) newValue;
            settingsPresenter.changeLocationUpdates(isEnabled);
            return true;
        });
    }

    private void configureNotificationsSwitch() {
        notificationsSwitch = (SwitchPreferenceCompat) getPreferenceManager().findPreference("notifications_prefs_key");
        notificationsSwitch.setOnPreferenceChangeListener((preference, newValue) -> {
            boolean isEnabled = (Boolean) newValue;
            settingsPresenter.changeMessagesState(isEnabled);
            return true;
        });
    }

    @Override
    public void onError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
