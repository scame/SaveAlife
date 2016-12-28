package com.krestone.savealife.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.emergency.EmergencyContactsAdapter;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.EmergencyPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyContactsFragment extends Fragment implements EmergencyPresenter.EmergencyView {

    @BindView(R.id.emergency_contacts_rv)
    RecyclerView contactsRv;

    @BindView(R.id.edit_emergency_btn)
    Button editEmergencyBtn;

    @Inject
    EmergencyPresenter<EmergencyPresenter.EmergencyView> emergencyPresenter;

    private EmergencyContactsAdapter contactsAdapter;

    private EmergencyListener emergencyListener;

    private List<ContactModel> contacts;

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
        inject();

        editEmergencyBtn.setOnClickListener(v -> emergencyListener.onEditEmergencyListClick());
        emergencyPresenter.setView(this);
        emergencyPresenter.requestEmergencyContacts();

        return fragmentView;
    }

    private void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideEmergencyComponent().inject(this);
        }
    }

    @Override
    public void displayEmergencyList(List<ContactModel> contacts) {
        this.contacts = contacts;

        contactsAdapter = new EmergencyContactsAdapter(contacts, getContext());
        contactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRv.setHasFixedSize(true);
        contactsRv.setAdapter(contactsAdapter);
    }

    @Override
    public void onDestroyView() {
        emergencyPresenter.destroy();
        super.onDestroyView();
    }
}
