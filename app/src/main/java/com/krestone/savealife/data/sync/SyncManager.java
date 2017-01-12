package com.krestone.savealife.data.sync;


import android.content.Context;

import com.krestone.savealife.data.sync.events.SyncType;

import java.util.HashMap;
import java.util.Map;

public class SyncManager {

    private final Map<SyncType, AbstractSync> syncMap;

    SyncManager(Context context) {
        syncMap = new HashMap<>();
        // all sync implementations go into syncMap
    }

    void doSync(SyncType syncType) {
        syncMap.get(syncType).sync();
    }
}
