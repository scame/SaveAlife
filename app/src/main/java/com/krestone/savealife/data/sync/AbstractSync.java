package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.sync.events.SyncEvent;
import com.krestone.savealife.data.sync.events.SyncStatus;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.util.ConnectivityUtil;

import rx.Completable;

public abstract class AbstractSync {

    private Context context;

    public AbstractSync(Context context) {
        this.context = context;
    }

    void sync() {
        if (ConnectivityUtil.isNetworkOn(context)) {
            Completable.defer(() -> sendInProgressEvent()
                    .andThen(post())
                    .andThen(get())
                    .andThen(sendCompletedEvent()))
                    .subscribe(this::sendCompletedEvent, throwable -> sendCompletedEvent());
        }
    }

    private Completable sendInProgressEvent() {
        return SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS, context);
    }

    private Completable sendCompletedEvent() {
        return SyncEvent.send(getSyncType(), SyncStatus.COMPLETED, context);
    }

    protected abstract SyncType getSyncType();

    protected abstract Completable post();

    protected abstract Completable get();
}
