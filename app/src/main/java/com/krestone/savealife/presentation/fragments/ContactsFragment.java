package com.krestone.savealife.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.activities.DrawerActivity;
import com.krestone.savealife.presentation.adapters.DividerItemDecoration;
import com.krestone.savealife.presentation.adapters.contacts.ContactsAdapter;
import com.krestone.savealife.presentation.models.ContactModel;
import com.krestone.savealife.presentation.presenters.ContactsPresenter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactsFragment extends Fragment implements ContactsPresenter.ContactsView {

    @BindView(R.id.contacts_rv)
    RecyclerView contactsRv;

    @Inject
    ContactsPresenter<ContactsPresenter.ContactsView> contactsPresenter;


    private ContactsAdapter contactsAdapter;

    private ActionMode actionMode;

    private final ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Title");
            mode.getMenuInflater().inflate(R.menu.contacts_actions, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.action_save:
                    Toast.makeText(getContext(), "Save", Toast.LENGTH_LONG).show();
                    mode.finish();
                    break;
                case R.id.action_cancel:
                    Toast.makeText(getContext(), "Cancel", Toast.LENGTH_SHORT).show();
                    mode.finish();
                    break;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.contacts_layout, container, false);
        ButterKnife.bind(this, fragmentView);

        inject();
        contactsPresenter.setView(this);
        contactsPresenter.requestContacts();
        setupActionMode();

        return fragmentView;
    }

    private void setupActionMode() {

    }

    private void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideContactsComponent().inject(this);
        }
    }

    @Override
    public void displayContacts(List<ContactModel> contacts) {
        contactsAdapter = new ContactsAdapter(getContext(), contacts, v -> Log.i("onxClick", "invite"), (buttonView, isChecked) -> {
            if (actionMode != null) return;
            actionMode = getActivity().startActionMode(actionCallback);
        });

        contactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRv.addItemDecoration(new DividerItemDecoration(getContext()));
        contactsRv.setHasFixedSize(true);
        contactsRv.setAdapter(contactsAdapter);
    }
}
