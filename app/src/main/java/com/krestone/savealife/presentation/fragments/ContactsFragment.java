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

    private List<ContactModel> contacts;

    private final ActionMode.Callback actionCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.setTitle("Actions");
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
                    contactsPresenter.saveUpdatedContacts(contacts);
                    mode.finish();
                    break;
                case R.id.action_cancel:
                    contactsPresenter.cancelChanges();
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

        return fragmentView;
    }

    private void inject() {
        if (getActivity() instanceof DrawerActivity) {
            ((DrawerActivity) getActivity()).provideContactsComponent().inject(this);
        }
    }

    @Override
    public void displayContacts(List<ContactModel> newContacts) {
        this.contacts = newContacts;
        contactsAdapter = new ContactsAdapter(getContext(), contacts, v -> Log.i("onxClick", "invite"), (buttonView, isChecked) -> {
            contactsPresenter.compareWithOldModel(contacts);
        });

        contactsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        contactsRv.addItemDecoration(new DividerItemDecoration(getContext()));
        contactsRv.setHasFixedSize(true);
        contactsRv.setAdapter(contactsAdapter);
    }

    @Override
    public void redrawList(List<ContactModel> newContacts) {
        contacts.clear();
        contacts.addAll(newContacts);
        contactsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDoneComparison(boolean isEqualToOld) {
        if (actionMode != null && isEqualToOld) {
            actionMode.finish();
            actionMode = null;
        } else if (actionMode == null) {
            actionMode = (getActivity()).findViewById(R.id.toolbar).startActionMode(actionCallback);
        }
    }
}
