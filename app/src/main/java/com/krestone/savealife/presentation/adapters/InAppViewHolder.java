package com.krestone.savealife.presentation.adapters;


import android.content.Context;
import android.view.View;

import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import butterknife.ButterKnife;

public class InAppViewHolder extends BaseContactViewHolder {

    public InAppViewHolder(View itemView, Context context) {
        super(context,itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    void bindHolder(List<ContactModel> contacts, int position) {
        contactName.setText(contacts.get(position).getName());
        handleCheckbox(contacts, position);
        handleProfileImage(contacts, position);
    }
}
