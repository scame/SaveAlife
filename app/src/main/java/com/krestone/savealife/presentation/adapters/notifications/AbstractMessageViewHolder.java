package com.krestone.savealife.presentation.adapters.notifications;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.krestone.savealife.R;
import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractMessageViewHolder extends RecyclerView.ViewHolder {

    NotificationsHandler notificationsHandler;

    Context context;

    @BindView(R.id.notif_iv)
    ImageView notificationIv;

    @BindView(R.id.notif_title)
    TextView notificationTitle;

    @BindView(R.id.notif_text)
    TextView notificationText;

    public AbstractMessageViewHolder(NotificationsHandler notificationsHandler,
                                     View itemView, Context context) {
        super(itemView);
        this.notificationsHandler = notificationsHandler;
        this.context = context;

        ButterKnife.bind(this, itemView);
    }

    abstract void bindViewHolder(AbstractMessage abstractMessage);
}
