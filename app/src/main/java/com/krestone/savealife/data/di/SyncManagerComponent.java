package com.krestone.savealife.data.di;


import com.krestone.savealife.data.sync.SyncService;

import dagger.Subcomponent;

@Subcomponent(modules = SyncManagerModule.class)
@PerService
public interface SyncManagerComponent {

    void inject(SyncService syncService);
}
