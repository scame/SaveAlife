package com.krestone.savealife.presentation.adapters.notifications;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.krestone.savealife.R;
import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;
import com.krestone.savealife.presentation.adapters.EmptyViewHolder;

import java.util.List;

public class NotificationsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SOS_MSG = 0;
    private static final int VIEW_TYPE_HELP_INTENT_MSG = 1;
    private static final int VIEW_TYPE_EMPTY = 2;

    private NotificationsHandler notificationsHandler;

    private List<AbstractMessage> messages;

    private Context context;

    public NotificationsAdapter(List<AbstractMessage> messages, Context context,
                                NotificationsHandler notificationsHandler) {
        this.notificationsHandler = notificationsHandler;
        this.messages = messages;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == VIEW_TYPE_SOS_MSG) {
            View itemView = inflater.inflate(R.layout.notification_item, parent, false);
            viewHolder = new SosMessageViewHolder(notificationsHandler, context, itemView);
        } else if (viewType == VIEW_TYPE_HELP_INTENT_MSG) {
            View itemView = inflater.inflate(R.layout.notification_item, parent, false);
            viewHolder = new HelpIntentViewHolder(notificationsHandler, context, itemView);
        } else if (viewType == VIEW_TYPE_EMPTY) {
            View itemView = inflater.inflate(R.layout.empty_notifications_rv, parent, false);
            viewHolder = new EmptyViewHolder(itemView);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AbstractMessageViewHolder) {
            ((AbstractMessageViewHolder) holder).bindViewHolder(messages.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (messages == null || messages.size() == 0) {
            return VIEW_TYPE_EMPTY;
        } else if (messages.get(position) instanceof SosMessageModel) {
            return VIEW_TYPE_SOS_MSG;
        } else if (messages.get(position) instanceof HelpIntentMessageModel) {
            return VIEW_TYPE_HELP_INTENT_MSG;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return messages == null ? 1 : messages.size() + 1;
    }
}
