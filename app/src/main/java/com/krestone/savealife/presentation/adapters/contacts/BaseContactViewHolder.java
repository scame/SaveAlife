package com.krestone.savealife.presentation.adapters.contacts;


import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseContactViewHolder extends RecyclerView.ViewHolder {

    protected Context context;

    @BindView(R.id.contact_profile_image)
    ImageView contactImage;

    @BindView(R.id.contact_name)
    TextView contactName;

    @BindView(R.id.emergency_checkbox)
    CheckBox emergencyBox;

    public BaseContactViewHolder(Context context, View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.context = context;
    }

    protected void handleProfileImage(List<ContactModel> contacts, int position) {
        String thumbnailUri = contacts.get(position).getThumbnailUri();
        if (thumbnailUri != null && !thumbnailUri.equals("")) {
            contactImage.setImageURI(Uri.parse(thumbnailUri));
        } else {
            contactImage.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
        }
    }

    protected void handleCheckbox(List<ContactModel> contacts, int position) {
        if (contacts.get(position).isInEmergencyList()) {
            emergencyBox.setChecked(true);
        } else {
            emergencyBox.setChecked(false);
        }
    }

    protected void bindHolder(List<ContactModel> contacts, int position) {
        contactName.setText(contacts.get(position).getName());
        handleCheckbox(contacts, position);
        handleProfileImage(contacts, position);
    }
}
