package com.krestone.savealife.data.sync;


import android.content.Context;
import android.util.Log;

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
            Completable.fromCallable(() -> sendInProgressEvent()
                            .andThen(post())
                            .andThen(get())
                            .andThen(sendCompletedEvent()))
                    .doOnError(throwable -> Log.i("onxSyncError", throwable.getLocalizedMessage()))
                    .await();
        } else {
             sendCompletedEvent();
        }
    }

    Completable completeWithErrorCheck(Throwable throwable) {
        if (throwable != null) {
            return Completable.error(throwable);
        }
        return Completable.complete();
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
