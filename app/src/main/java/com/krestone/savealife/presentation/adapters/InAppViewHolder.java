package com.krestone.savealife.presentation.adapters;


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

public class InAppViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_profile_image)
    ImageView contactImage;

    @BindView(R.id.contact_name)
    TextView contactName;

    @BindView(R.id.emergency_checkbox)
    CheckBox emergencyBox;

    private Context context;

    public InAppViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
    }

    void bindHolder(List<ContactModel> contacts, int position) {
        contactName.setText(contacts.get(position).getName());
        handleCheckbox(contacts, position);
        handleProfileImage(contacts, position);
    }

    private void handleProfileImage(List<ContactModel> contacts, int position) {
        String thumbnailUri = contacts.get(position).getThumbnailUri();
        if (thumbnailUri != null && !thumbnailUri.equals("")) {
            contactImage.setImageURI(Uri.parse(thumbnailUri));
        } else {
            contactImage.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
        }
    }

    private void handleCheckbox(List<ContactModel> contacts, int position) {
        if (contacts.get(position).isInEmergencyList()) {
            emergencyBox.setChecked(true);
        } else {
            emergencyBox.setChecked(false);
        }
    }
}
