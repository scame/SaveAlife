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

public class AddToEmergencyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_NORMAL = 0;
    private static final int VIEW_TYPE_EMPTY = 1;

    private ListItemClickListener addToListListener;

    private List<ContactModel> contacts;

    private Context context;

    public AddToEmergencyAdapter(Context context, List<ContactModel> contacts,
                                 ListItemClickListener addToListListener) {
        this.addToListListener = addToListListener;
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_NORMAL) {
            View itemView = inflater.inflate(R.layout.possible_contact_item, parent, false);
            viewHolder = new AddToEmergencyViewHolder(itemView, addToListListener, context);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            View itemView = inflater.inflate(R.layout.empty_possible_contacts_rv, parent, false);
            viewHolder = new EmptyViewHolder(itemView);
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((AddToEmergencyViewHolder) holder).bindHolder(contacts, position);
    }

    @Override
    public int getItemCount() {
        if (contacts == null || contacts.size() == 0) {
            return 1;
        }
        return contacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (contacts == null || contacts.size() == 0) {
            return VIEW_TYPE_EMPTY;
        }
        return VIEW_TYPE_NORMAL;
    }
}
