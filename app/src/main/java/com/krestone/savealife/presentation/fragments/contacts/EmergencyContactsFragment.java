package com.krestone.savealife.presentation.fragments.contacts;


import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.data.sync.SyncService;
import com.krestone.savealife.data.sync.events.BroadcastsMeta;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.DividerItemDecoration;
import com.krestone.savealife.presentation.adapters.emergency.EmergencyContactsAdapter;
import com.krestone.savealife.presentation.fragments.AbstractFragment;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.contacts.EmergencyPresenter;
import com.krestone.savealife.presentation.receivers.SyncEventReceiver;
import com.krestone.savealife.util.InvitationUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import icepick.State;

public class EmergencyContactsFragment extends AbstractFragment implements EmergencyPresenter.EmergencyView {

    @BindView(R.id.emergency_contacts_rv)
    RecyclerView contactsRv;

    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout swipeView;

    @BindView(R.id.add_fab)
    FloatingActionButton addFab;

    @Inject
    EmergencyPresenter<EmergencyPresenter.EmergencyView> emergencyPresenter;

    private EmergencyContactsAdapter contactsAdapter;

    private EmergencyListener emergencyListener;

    @State
    ArrayList<ContactModel> contacts;

    private SyncEventReceiver receiver;

    public interface EmergencyListener {

        void onAddToEmergencyListClick();

        void onContactProfileClick(String phoneNumber);
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

        swipeView.setOnRefreshListener(() -> SyncService.requestSync(SyncType.CONTACTS, getContext()));
        addFab.setOnClickListener(v -> emergencyListener.onAddToEmergencyListClick());
        emergencyPresenter.setView(this);
        instantiateFragment();
        createReceiver();

        return fragmentView;
    }

    private void createReceiver() {
        receiver = new SyncEventReceiver(swipeView, SyncType.CONTACTS) {
            @Override
            public void syncLocally() {
                emergencyPresenter.requestEmergencyContacts();
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(getContext())
                .registerReceiver(receiver, new IntentFilter(BroadcastsMeta.SYNC_RESPONSE));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
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
    public void displayEmergencyList(List<ContactModel> contacts) {
        this.contacts = new ArrayList<>(contacts);

        contactsAdapter = new EmergencyContactsAdapter(this.contacts, getContext(), adapterPosition ->
                emergencyListener.onContactProfileClick(contacts.get(adapterPosition).getPhoneNumber()),
                v -> InvitationUtil.showInviteWindow(getContext(), ""));

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
