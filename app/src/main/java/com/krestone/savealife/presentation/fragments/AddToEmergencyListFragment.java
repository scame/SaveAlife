package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.DividerItemDecoration;
import com.krestone.savealife.presentation.adapters.emergency.AddToEmergencyAdapter;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.contacts.AddToEmergencyListPresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

public class AddToEmergencyListFragment extends AbstractFragment implements AddToEmergencyListPresenter.AddToEmergencyListView {

    @BindView(R.id.possible_contacts_rv)
    RecyclerView contactsRv;

    @Inject
    AddToEmergencyListPresenter<AddToEmergencyListPresenter.AddToEmergencyListView> presenter;

    private AddToEmergencyAdapter addToEmergencyAdapter;

    @State
    ArrayList<ContactModel> contacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        presenter.setView(this);
        instantiateFragment();

        return fragmentView;
    }

    private void instantiateFragment() {
        if (contacts == null) {
            presenter.requestContacts();
        } else {
            displayContacts(contacts);
        }
    }

    protected void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideContactsComponent().inject(this);
        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.add_to_emergency_layout;
    }

    @Override
    public void displayContacts(List<ContactModel> contacts) {
        this.contacts = new ArrayList<>(contacts);
        addToEmergencyAdapter = new AddToEmergencyAdapter(getContext(), this.contacts, adapterPosition -> {
            // TODO: handle adding to emergency list
        });

        contactsRv.setHasFixedSize(true);
        contactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRv.addItemDecoration(new DividerItemDecoration(getContext()));
        contactsRv.setAdapter(addToEmergencyAdapter);
    }


    @Override
    public void onDestroyView() {
        presenter.destroy();
        super.onDestroyView();
    }
}
