package com.krestone.savealife.presentation.adapters.notifications;


import android.content.Context;
import android.view.View;

import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;

public class HelpIntentViewHolder extends AbstractMessageViewHolder {

    public HelpIntentViewHolder(NotificationsHandler notificationsHandler,
                                Context context, View itemView) {
        super(notificationsHandler, itemView, context);
    }

    @Override
    void bindViewHolder(AbstractMessage abstractMessage) {
        HelpIntentMessageModel message = (HelpIntentMessageModel) abstractMessage;

        String msgText = notificationsHandler.formatIntentMessageContent(message);
        String msgTitle = notificationsHandler.formatIntentMessageTitle(message);

        notificationTitle.setText(msgTitle);
        notificationText.setText(msgText);
    }
}
