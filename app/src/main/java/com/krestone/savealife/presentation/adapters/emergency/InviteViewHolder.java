package com.krestone.savealife.presentation.adapters.emergency;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public class InviteViewHolder extends RecyclerView.ViewHolder {

    public InviteViewHolder(View itemView, View.OnClickListener inviteClick) {
        super(itemView);
        itemView.setOnClickListener(inviteClick);
    }
}
