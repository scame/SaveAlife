package com.krestone.savealife.presentation.adapters.emergency;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.presentation.adapters.ListItemClickListener;
import com.krestone.savealife.presentation.models.ContactModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EmergencyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.contact_name)
    TextView contactName;

    @BindView(R.id.contact_profile_image)
    ImageView contactImage;

    @BindView(R.id.is_in_app_iv)
    ImageView isInAppTv;

    private Context context;

    public EmergencyViewHolder(View itemView, Context context, ListItemClickListener itemClickListener) {
        super(itemView);
        this.context = context;
        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(v -> itemClickListener.onItemClick(getAdapterPosition()));
    }

    protected void bindHolder(List<ContactModel> contacts, int position) {
        ContactModel item = contacts.get(position);
        contactName.setText(item.getFirstName());

        if (item.getInApp() == null) {
            isInAppTv.setImageDrawable(context.getResources().getDrawable(R.drawable.placeholder));
        } else if (item.getInApp()) {
            isInAppTv.setImageDrawable(context.getResources().getDrawable(R.drawable.green_background));
        } else if (!item.getInApp()) {
            isInAppTv.setImageDrawable(context.getResources().getDrawable(R.drawable.red_background));
        }
    }
}
