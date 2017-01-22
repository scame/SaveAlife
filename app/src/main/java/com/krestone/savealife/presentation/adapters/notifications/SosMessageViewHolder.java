package com.krestone.savealife.presentation.adapters.notifications;


import android.content.Context;
import android.view.View;

import com.krestone.savealife.data.messages.NotificationsHandler;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;

public class SosMessageViewHolder extends AbstractMessageViewHolder {

    public SosMessageViewHolder(NotificationsHandler notificationsHandler,
                                Context context, View itemView) {
        super(notificationsHandler,itemView, context);
    }

    @Override
    void bindViewHolder(AbstractMessage abstractMessage) {
        SosMessageModel message = (SosMessageModel) abstractMessage;

        String msgTitle = notificationsHandler.formatSosMessageTitle(message);
        String msgText = notificationsHandler.formatSosMessageContent(message);

        notificationTitle.setText(msgTitle);
        notificationText.setText(msgText);
    }
}
