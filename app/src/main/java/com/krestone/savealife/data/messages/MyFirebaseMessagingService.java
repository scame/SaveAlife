package com.krestone.savealife.data.messages;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;
import com.krestone.savealife.presentation.activities.DrawerActivity;

import javax.inject.Inject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Inject
    MessagesRepository messagesRepository;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveAlifeApplication.getAppComponent().inject(this);
    }

    /** runs in the background, so it's safe to use blocking operations */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0) {
            messagesRepository.parseFirebaseMessage(remoteMessage.getData())
                    .subscribe(message -> {
                        if (message.getMessageType() == AbstractMessage.SOS_MESSAGE) {
                            showSosNotification((SosMessageModel) message);
                        } else if (message.getMessageType() == AbstractMessage.HELP_INTENT_MESSAGE) {
                            showHelpIntentNotification((HelpIntentMessageModel) message);
                        }
                    }, throwable -> Log.i("onxMessageErr", throwable.getLocalizedMessage()));

            messagesRepository.saveFirebaseMessage(remoteMessage.getData()).await();
        }
    }

    private void showSosNotification(SosMessageModel sosMessageModel) {

    }

    private void showHelpIntentNotification(HelpIntentMessageModel helpIntentMessageModel) {

    }

    private void sendNotification(String messageBody) {
        Intent intent = new Intent(this, DrawerActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_add_black_24dp)
                .setContentTitle("FCM Message")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
