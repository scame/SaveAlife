package com.krestone.savealife.presentation.adapters;


import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotInAppViewHolder extends BaseContactViewHolder {

    @BindView(R.id.invite_button)
    Button inviteButton;

    public NotInAppViewHolder(Context context, View itemView, View.OnClickListener inviteListener) {
        super(context, itemView);
        ButterKnife.bind(this, itemView);
        inviteButton.setOnClickListener(inviteListener);
    }

    void bindHolder(List<ContactModel> contacts, int position) {
        contactName.setText(contacts.get(position).getName());
        handleCheckbox(contacts, position);
        handleProfileImage(contacts, position);
    }
}
