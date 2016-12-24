package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.domain.usecases.GetEmergencyContactsUseCase;

public class EmergencyPresenterImp<T extends EmergencyPresenter.EmergencyView> implements EmergencyPresenter<T> {

    private T view;

    private GetEmergencyContactsUseCase getEmergencyContactsUseCase;

    public EmergencyPresenterImp(GetEmergencyContactsUseCase getEmergencyContactsUseCase) {
        this.getEmergencyContactsUseCase = getEmergencyContactsUseCase;
    }

    @Override
    public void requestEmergencyContacts() {
        getEmergencyContactsUseCase.executeSingle(contactModels -> {
            if (view != null) {
                //view.displayEmergencyList(contactModels);
            }
        }, throwable -> Log.i("onxEmergencyErr", throwable.getLocalizedMessage()));
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        getEmergencyContactsUseCase.unsubscribe();
    }
}
