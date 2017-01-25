package com.krestone.savealife.domain.usecases.settings;


import com.krestone.savealife.data.repository.SettingsRepository;
import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;
import com.krestone.savealife.domain.usecases.base.UseCaseCompletable;

import rx.Completable;

public class ChangeLocationUpdatesUseCase extends UseCaseCompletable {

    private boolean isEnabled;

    private SettingsRepository settingsRepository;

    public ChangeLocationUpdatesUseCase(SubscribeOn subscribeOn, ObserveOn observeOn,
                                        SettingsRepository settingsRepository) {
        super(subscribeOn, observeOn);
        this.settingsRepository = settingsRepository;
    }

    @Override
    protected Completable getUseCaseCompletable() {
        return settingsRepository.changeLocationsUpdates(isEnabled);
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }
}
