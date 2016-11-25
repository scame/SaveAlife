package com.krestone.savealife.presentation.adapters.contacts;


import android.content.Context;
import android.view.View;
import android.widget.CompoundButton;

import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

public class InAppViewHolder extends BaseContactViewHolder {

    public InAppViewHolder(View itemView, Context context, CompoundButton.OnCheckedChangeListener checkboxListener,
                           List<ContactModel> contacts) {
        super(context,itemView, checkboxListener, contacts);
    }
}
