package com.krestone.savealife.presentation.adapters.emergency;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.data.entities.responses.ContactItem;
import com.krestone.savealife.presentation.adapters.ListItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_name)
    TextView contactName;

    @BindView(R.id.contact_profile_image)
    ImageView contactImage;

    @BindView(R.id.is_in_app)
    TextView isInAppTv;

    private Context context;

    public EmergencyViewHolder(View itemView, Context context, ListItemClickListener itemClickListener) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> itemClickListener.onItemClick(getAdapterPosition()));
    }

    protected void bindHolder(List<ContactItem> contacts, int position) {
        ContactItem item = contacts.get(position);
        contactName.setText(item.getName());
        if (item.getInApp()) {
            isInAppTv.setText(context.getString(R.string.in_app));
        } else {
            isInAppTv.setText(context.getString(R.string.not_in_app));
        }
    }
}
