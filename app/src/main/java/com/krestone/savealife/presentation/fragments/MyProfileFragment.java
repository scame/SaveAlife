package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProfileFragment extends Fragment {

    @BindView(R.id.profile_qualifications_spinner)
    MaterialSpinner qualificationSpinner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.profile_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        qualificationSpinner.setItems("No medical qualification", "Surgeon", "Nurse");

        return fragmentView;
    }
}
