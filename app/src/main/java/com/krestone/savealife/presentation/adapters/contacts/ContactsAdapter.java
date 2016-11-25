package com.krestone.savealife.presentation.adapters.contacts;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_IN_APP = 0;
    private static final int VIEW_TYPE_NOT_IN_APP = 1;

    private List<ContactModel> contacts;

    private View.OnClickListener inviteListener;

    private CompoundButton.OnCheckedChangeListener checkboxListener;

    private Context context;

    public ContactsAdapter(Context context, List<ContactModel> contacts, View.OnClickListener inviteListener,
                           CompoundButton.OnCheckedChangeListener checkboxListener) {
        this.checkboxListener = checkboxListener;
        this.inviteListener = inviteListener;
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_IN_APP) {
            View itemView = inflater.inflate(R.layout.contact_item_in_app, parent, false);
            viewHolder = new InAppViewHolder(itemView, context, checkboxListener, contacts);
        } else if (viewType == VIEW_TYPE_NOT_IN_APP) {
            View itemView = inflater.inflate(R.layout.contact_item_not_in_app, parent, false);
            viewHolder = new NotInAppViewHolder(context, itemView, inviteListener, checkboxListener, contacts);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InAppViewHolder) {
            ((InAppViewHolder) holder).bindHolder(contacts, position);
        } else if (holder instanceof NotInAppViewHolder) {
            ((NotInAppViewHolder) holder).bindHolder(contacts, position);
        }
    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return contacts.get(position).isInApp() ? VIEW_TYPE_IN_APP : VIEW_TYPE_NOT_IN_APP;
    }
}
