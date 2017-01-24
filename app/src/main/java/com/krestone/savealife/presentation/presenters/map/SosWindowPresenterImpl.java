package com.krestone.savealife.presentation.presenters.map;


import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.messages.StartHelpUseCase;
import com.krestone.savealife.util.ConnectivityUtil;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class SosWindowPresenterImpl<T extends SosWindowPresenter.SosWindowView> implements SosWindowPresenter<T> {

    private T view;

    private StartHelpUseCase startHelpUseCase;

    public SosWindowPresenterImpl(StartHelpUseCase startHelpUseCase) {
        this.startHelpUseCase = startHelpUseCase;
    }

    public void promoteHelpIntent(LatLng targetLatLng, String number, boolean isHelp) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            startHelpUseCase.setPhoneNumber(number);
            startHelpUseCase.setTargetLatLng(targetLatLng);
            promoteHelpIntent();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void promoteHelpIntent() {
        startHelpUseCase.executeSingle(helpIntentWrapper -> {
            if (view != null) view.onHelpPressed(helpIntentWrapper.getRouteModel().getPolyline());
        }, throwable -> {
            if (view != null) view.onError(throwable.getLocalizedMessage());
        });
    }

    @Override
    public void progressDialogCancel() {
        startHelpUseCase.unsubscribe();
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        startHelpUseCase.unsubscribe();
    }
}
