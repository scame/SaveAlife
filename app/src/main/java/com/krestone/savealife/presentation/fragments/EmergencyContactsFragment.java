package com.krestone.savealife.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.adapters.emergency.EmergencyContactsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyContactsFragment extends Fragment {

    @BindView(R.id.emergency_contacts_rv)
    RecyclerView contactsRv;

    @BindView(R.id.edit_emergency_btn)
    Button editEmergencyBtn;

    private EmergencyContactsAdapter contactsAdapter;

    private EmergencyListener emergencyListener;

    public interface EmergencyListener {

        void onEditEmergencyListClick();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EmergencyListener) {
            emergencyListener = (EmergencyListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.emergency_contacts_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        editEmergencyBtn.setOnClickListener(v -> emergencyListener.onEditEmergencyListClick());

        return fragmentView;
    }
}
