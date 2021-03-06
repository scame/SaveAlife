package com.krestone.savealife.data.sync;


import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.di.SyncManagerModule;
import com.krestone.savealife.data.sync.events.BroadcastsMeta;
import com.krestone.savealife.data.sync.events.SyncType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

import static com.krestone.savealife.data.sync.events.BroadcastsMeta.SYNC_REQUEST;

public class SyncService extends Service {

    @Inject
    SyncManager syncManager;

    private ExecutorService executorService;

    @Override
    public void onCreate() {
        super.onCreate();
        executorService = Executors.newSingleThreadExecutor();
        inject();

        LocalBroadcastManager.getInstance(this).registerReceiver(syncEventReceiver, new IntentFilter(SYNC_REQUEST));
    }

    private void inject() {
        SaveAlifeApplication.getAppComponent().provideSyncManagerComponent(new SyncManagerModule()).inject(this);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(syncEventReceiver);
        super.onDestroy();
    }

    private final BroadcastReceiver syncEventReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            executorService.execute(() -> {
                SyncType syncType = (SyncType) intent.getSerializableExtra(BroadcastsMeta.SYNC_REQUEST_EXTRA);
                syncManager.doSync(syncType);
            });
        }
    };

    public static void start(@NonNull Context context) {
        Intent intent = new Intent(context, SyncService.class);
        context.startService(intent);
    }

    public static void requestSync(@NonNull SyncType syncType, Context context) {
        Intent syncIntent = new Intent(SYNC_REQUEST);

        syncIntent.putExtra(BroadcastsMeta.SYNC_REQUEST_EXTRA, syncType);
        LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(syncIntent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
