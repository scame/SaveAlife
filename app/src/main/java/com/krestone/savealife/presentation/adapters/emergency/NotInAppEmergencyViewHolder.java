package com.krestone.savealife.presentation.adapters.emergency;


import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.krestone.savealife.R;

import butterknife.BindView;

public class NotInAppEmergencyViewHolder extends BaseEmergencyViewHolder {

    @BindView(R.id.invite_button)
    Button inviteButton;

    public NotInAppEmergencyViewHolder(View itemView, Context context, View.OnClickListener inviteListener) {
        super(itemView, context);
        inviteButton.setOnClickListener(inviteListener);
    }
}
