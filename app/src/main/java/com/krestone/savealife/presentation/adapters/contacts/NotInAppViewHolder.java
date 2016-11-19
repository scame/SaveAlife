package com.krestone.savealife.presentation.adapters.contacts;


import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.krestone.savealife.R;

import butterknife.BindView;

public class NotInAppViewHolder extends BaseContactViewHolder {

    @BindView(R.id.invite_button)
    Button inviteButton;

    public NotInAppViewHolder(Context context, View itemView, View.OnClickListener inviteListener) {
        super(context, itemView);
        inviteButton.setOnClickListener(inviteListener);
    }
}
