package com.krestone.savealife.presentation.adapters.emergency;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.adapters.EmptyViewHolder;
import com.krestone.savealife.presentation.adapters.ListItemClickListener;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class EmergencyContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_INVITE = 0;
    private static final int VIEW_TYPE_CONTACT = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    private ListItemClickListener listItemClickListener;

    private View.OnClickListener inviteClick;

    private List<ContactModel> contacts;

    private Context context;

    public EmergencyContactsAdapter(List<ContactModel> contacts, Context context,
                                    ListItemClickListener listItemClickListener,
                                    View.OnClickListener inviteClick) {
        this.listItemClickListener = listItemClickListener;
        this.inviteClick = inviteClick;
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_INVITE) {
            View itemView = inflater.inflate(R.layout.invite_item, parent, false);
            viewHolder = new InviteViewHolder(itemView, inviteClick);
        } else if (viewType == VIEW_TYPE_CONTACT) {
            View itemView = inflater.inflate(R.layout.emergency_contact_item, parent, false);
            viewHolder = new EmergencyViewHolder(itemView, context, listItemClickListener);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            View itemView = inflater.inflate(R.layout.empty_contacts_rv_layout, parent, false);
            viewHolder = new EmptyViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof EmergencyViewHolder) {
            ((EmergencyViewHolder) holder).bindHolder(contacts, position);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 1 : contacts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts == null || contacts.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return position == contacts.size() ? VIEW_TYPE_INVITE : VIEW_TYPE_CONTACT;
    }
}
