package com.krestone.savealife.presentation.adapters.emergency;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.krestone.savealife.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RefreshViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.refresh_contacts)
    Button refreshButton;

    public RefreshViewHolder(View itemView, View.OnClickListener refreshListener) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        refreshButton.setOnClickListener(refreshListener);
    }
}
