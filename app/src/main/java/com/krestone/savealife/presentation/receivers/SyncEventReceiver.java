package com.krestone.savealife.presentation.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;

import com.krestone.savealife.data.sync.events.BroadcastsMeta;
import com.krestone.savealife.data.sync.events.SyncEvent;
import com.krestone.savealife.data.sync.events.SyncStatus;
import com.krestone.savealife.data.sync.events.SyncType;

public abstract class SyncEventReceiver extends BroadcastReceiver {

    private SwipeRefreshLayout swipeRefreshLayout;

    private SyncType syncType;

    public SyncEventReceiver(SwipeRefreshLayout swipeRefreshLayout, SyncType syncType) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.syncType = syncType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SyncEvent event = intent.getParcelableExtra(BroadcastsMeta.SYNC_RESPONSE_EXTRA);
        if (event.getSyncType() == syncType && event.getSyncStatus() == SyncStatus.IN_PROGRESS) {
            if (!swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(true);
            }
        } else if (event.getSyncType() == syncType && event.getSyncStatus() == SyncStatus.COMPLETED) {
            swipeRefreshLayout.setRefreshing(false);
            syncLocally();
        }
    }

    public abstract void syncLocally();
}
