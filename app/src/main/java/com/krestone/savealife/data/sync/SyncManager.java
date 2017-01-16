package com.krestone.savealife.data.sync;


import com.krestone.savealife.data.sync.events.SyncType;

import java.util.HashMap;
import java.util.Map;

public class SyncManager {

    private final Map<SyncType, AbstractSync> syncMap;

    public SyncManager(EmergencyContactsSync emergencyContactsSync, ProfileSync profileSync) {
        syncMap = new HashMap<>();
        syncMap.put(SyncType.CONTACTS, emergencyContactsSync);
        syncMap.put(SyncType.PROFILE, profileSync);
    }

    void doSync(SyncType syncType) {
        syncMap.get(syncType).sync();
    }
}
