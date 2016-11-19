package com.krestone.savealife.presentation.adapters.emergency;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_IN_APP = 1;
    private static final int VIEW_TYPE_NOT_IN_APP = 2;

    private static final int NUMBER_OF_VIEW_ABOVE = 1;

    private List<ContactModel> contacts;

    private View.OnClickListener inviteListener;

    private Context context;

    public EmergencyContactsAdapter(List<ContactModel> contacts, Context context, View.OnClickListener inviteListener) {
        this.contacts = contacts;
        this.context = context;
        this.inviteListener = inviteListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_HEADER) {
            View itemView = inflater.inflate(R.layout.emergency_header_item, parent, false);
            viewHolder = new EmergencyHeaderViewHolder(itemView);
        } else if (viewType == VIEW_TYPE_IN_APP) {
            View itemView = inflater.inflate(R.layout.emergency_contact_item_in_app, parent, false);
            viewHolder = new InAppEmergencyViewHolder(itemView, context);
        } else if (viewType == VIEW_TYPE_NOT_IN_APP) {
            View itemView = inflater.inflate(R.layout.emergency_contact_item_not_in_app, parent, false);
            viewHolder = new NotInAppEmergencyViewHolder(itemView, context, inviteListener);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InAppEmergencyViewHolder) {
            ((InAppEmergencyViewHolder) holder).bindHolder(contacts, position - NUMBER_OF_VIEW_ABOVE);
        } else if (holder instanceof NotInAppEmergencyViewHolder) {
            ((NotInAppEmergencyViewHolder) holder).bindHolder(contacts, position - NUMBER_OF_VIEW_ABOVE);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? NUMBER_OF_VIEW_ABOVE : contacts.size() + NUMBER_OF_VIEW_ABOVE;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == VIEW_TYPE_HEADER) {
            return VIEW_TYPE_HEADER;
        } else if (contacts.get(position - NUMBER_OF_VIEW_ABOVE).isInApp()) {
            return VIEW_TYPE_IN_APP;
        } else {
            return VIEW_TYPE_NOT_IN_APP;
        }
    }
}
