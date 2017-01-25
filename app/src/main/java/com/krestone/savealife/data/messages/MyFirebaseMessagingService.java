package com.krestone.savealife.data.messages;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.repository.MessagesRepository;
import com.krestone.savealife.data.sqlite.models.AbstractMessage;
import com.krestone.savealife.data.sqlite.models.HelpIntentMessageModel;
import com.krestone.savealife.data.sqlite.models.SosMessageModel;

import javax.inject.Inject;

import rx.Completable;


// FIXME: 1/25/17 if app process crash then we could get inconsistent static variable, should be read from prefs
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    private static boolean isEnabled = true;

    @Inject
    MessagesRepository messagesRepository;

    @Inject
    NotificationsHandler notificationsHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveAlifeApplication.getAppComponent().inject(this);
    }

    /** runs in the background, so it's safe to use blocking operations */

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getData().size() > 0 && isEnabled) {
            messagesRepository.parseFirebaseMessage(remoteMessage.getData())
                    .subscribe(message -> {
                        if (message.getMessageType() == AbstractMessage.SOS_MESSAGE) {
                            notificationsHandler.showSosNotification((SosMessageModel) message);
                        } else if (message.getMessageType() == AbstractMessage.HELP_INTENT_MESSAGE) {
                            notificationsHandler.showHelpIntentNotification((HelpIntentMessageModel) message);
                        }
                    }, throwable -> Log.i("onxMessageErr", throwable.getLocalizedMessage()));

            messagesRepository.saveFirebaseMessage(remoteMessage.getData()).await();
        }
    }

    public static Completable changesMessagingServiceState(boolean isEnabled) {
        MyFirebaseMessagingService.isEnabled = isEnabled;
        return Completable.complete();
    }
}
