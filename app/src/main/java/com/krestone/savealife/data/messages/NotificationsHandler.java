package com.krestone.savealife.data.messages;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.krestone.savealife.R;
import com.krestone.savealife.data.sqlite.MessagesTable;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;
import com.krestone.savealife.presentation.activities.DrawerActivity;

public class NotificationsHandler {

    public static final String MESSAGE_EXTRA = "notifExtra";
    public static final Integer SOS_MESSAGE_ID = 0;
    public static final Integer HELP_INTENT_MESSAGE_ID = 1;

    public static final String SOS_NUMBER_EXTRA = "sosNumberExtra";

    private Context context;

    public NotificationsHandler(Context context) {
        this.context = context.getApplicationContext();
    }

    void showSosNotification(SosMessageModel sosMessageModel) {
        Intent intent = new Intent(context, DrawerActivity.class);
        intent.putExtra(MESSAGE_EXTRA, SOS_MESSAGE_ID);
        intent.putExtra(SOS_NUMBER_EXTRA, sosMessageModel.getPhoneNumber());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        String contentTitle = formatSosMessageTitle(sosMessageModel);
        String contentText = formatSosMessageContent(sosMessageModel);
        int smallIconId = R.drawable.ic_add_black_24dp;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, SOS_MESSAGE_ID, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification sosNotification = buildNotification(smallIconId, contentTitle, contentText, pendingIntent);
        showNotification(SOS_MESSAGE_ID, sosNotification);
    }

    private String formatSosMessageContent(SosMessageModel sosMessageModel) {
        return sosMessageModel.getGlobalMessageType() == MessagesTable.MESSAGE_TYPE_SOS_START ?
                "Needs a help" : "Doesn't need a help anymore";
    }

    private String formatSosMessageTitle(SosMessageModel sosMessageModel) {
        return "SAL: " + sosMessageModel.getFirstName() + " " + sosMessageModel.getLastName() + " "
                + " " + sosMessageModel.getPhoneNumber();
    }

    void showHelpIntentNotification(HelpIntentMessageModel helpIntentMessageModel) {
        Intent intent = new Intent(context, DrawerActivity.class);
        intent.putExtra(MESSAGE_EXTRA, HELP_INTENT_MESSAGE_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        String contentTitle = formatIntentMessageTitle(helpIntentMessageModel);
        String contentText = formatIntentMessageContent(helpIntentMessageModel);
        int smallIconId = R.drawable.ic_add_black_24dp;

        PendingIntent pendingIntent = PendingIntent.getActivity(context, HELP_INTENT_MESSAGE_ID, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Notification helpIntentNotif = buildNotification(smallIconId, contentTitle, contentText, pendingIntent);
        showNotification(HELP_INTENT_MESSAGE_ID, helpIntentNotif);
    }

    private String formatIntentMessageTitle(HelpIntentMessageModel helpIntentMessage) {
        return "SAL: " + helpIntentMessage.getFirstName() + " " + helpIntentMessage.getLastName() + " "
                + " " + helpIntentMessage.getPhoneNumber();
    }

    private String formatIntentMessageContent(HelpIntentMessageModel helpIntentMessage) {
        return helpIntentMessage.getGlobalMessageType() == MessagesTable.MESSAGE_TYPE_INTENT_START ?
                "Is going to help (" + helpIntentMessage.getDistance() + " from you)" : "Decided not to help";
    }

    private Notification buildNotification(int smallIconId, String title, String text, PendingIntent pendingIntent) {
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        return new NotificationCompat.Builder(context)
                .setSmallIcon(smallIconId)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();
    }

    private void showNotification(int notificationId, Notification notification) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationId, notification);
    }
}
