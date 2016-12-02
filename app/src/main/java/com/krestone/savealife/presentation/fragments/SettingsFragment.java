package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.krestone.savealife.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }
}
