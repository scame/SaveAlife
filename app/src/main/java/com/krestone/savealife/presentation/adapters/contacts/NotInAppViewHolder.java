package com.krestone.savealife.presentation.adapters.contacts;


import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import butterknife.BindView;

public class NotInAppViewHolder extends BaseContactViewHolder {

    @BindView(R.id.invite_button)
    Button inviteButton;

    public NotInAppViewHolder(Context context, View itemView, View.OnClickListener inviteListener,
                              CompoundButton.OnCheckedChangeListener checkboxListener, List<ContactModel> contacts) {
        super(context, itemView, checkboxListener, contacts);
        inviteButton.setOnClickListener(inviteListener);
    }
}
