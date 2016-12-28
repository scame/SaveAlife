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

    private List<ContactModel> contacts;

    private Context context;

    public EmergencyContactsAdapter(List<ContactModel> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.emergency_contact_item, parent, false);

        return new EmergencyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return contacts == null ? 0 : contacts.size();
    }
}
