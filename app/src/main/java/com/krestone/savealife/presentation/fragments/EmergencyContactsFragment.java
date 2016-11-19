package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.adapters.emergency.EmergencyContactsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyContactsFragment extends Fragment {

    @BindView(R.id.emergency_contacts_rv)
    RecyclerView contactsRv;

    private EmergencyContactsAdapter contactsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.emergency_contacts_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        return fragmentView;
    }
}
