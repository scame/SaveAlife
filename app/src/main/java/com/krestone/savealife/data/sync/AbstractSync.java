package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.sync.events.SyncEvent;
import com.krestone.savealife.data.sync.events.SyncStatus;
import com.krestone.savealife.data.sync.events.SyncType;
import com.krestone.savealife.util.ConnectivityUtil;

public abstract class AbstractSync {

    private Context context;

    public AbstractSync(Context context) {
        this.context = context;
    }

    void sync() {
        if (ConnectivityUtil.isNetworkOn(context)) {
            SyncEvent.send(getSyncType(), SyncStatus.IN_PROGRESS, context);
            post();
            /*get();
            SyncEvent.send(getSyncType(), SyncStatus.COMPLETED, context);*/
        }
    }

    protected abstract SyncType getSyncType();

    protected abstract void post();
    protected abstract void get();
}
