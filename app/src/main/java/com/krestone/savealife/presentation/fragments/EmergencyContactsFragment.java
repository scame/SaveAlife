package com.krestone.savealife.presentation.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.DividerItemDecoration;
import com.krestone.savealife.presentation.adapters.emergency.EmergencyContactsAdapter;
import com.krestone.savealife.presentation.presenters.contacts.EmergencyPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

public class EmergencyContactsFragment extends AbstractFragment implements EmergencyPresenter.EmergencyView {

    @BindView(R.id.emergency_contacts_rv)
    RecyclerView contactsRv;

    @BindView(R.id.add_fab)
    FloatingActionButton addFab;

    @Inject
    EmergencyPresenter<EmergencyPresenter.EmergencyView> emergencyPresenter;

    private EmergencyContactsAdapter contactsAdapter;

    private EmergencyListener emergencyListener;

    @State
    ArrayList<ContactItem> contacts;

    public interface EmergencyListener {

        void onAddToEmergencyListClick();
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
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        addFab.setOnClickListener(v -> emergencyListener.onAddToEmergencyListClick());
        emergencyPresenter.setView(this);
        instantiateFragment();

        return fragmentView;
    }

    private void instantiateFragment() {
        if (contacts == null) {
            emergencyPresenter.requestEmergencyContacts();
        } else {
            displayEmergencyList(contacts);
        }
    }

    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideEmergencyComponent().inject(this);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.emergency_contacts_layout;
    }

    @Override
    public void displayEmergencyList(List<ContactItem> contacts) {
        this.contacts = new ArrayList<>(contacts);

        contactsAdapter = new EmergencyContactsAdapter(this.contacts, getContext(), adapterPosition -> {
            // TODO: handle list item click
        }, v -> {
            // TODO: handle invite click
        });
        contactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRv.addItemDecoration(new DividerItemDecoration(getContext()));
        contactsRv.setAdapter(contactsAdapter);
    }


    @Override
    public void onDestroyView() {
        emergencyPresenter.destroy();
        super.onDestroyView();
    }
}
