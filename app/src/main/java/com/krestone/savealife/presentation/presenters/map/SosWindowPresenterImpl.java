package com.krestone.savealife.presentation.presenters.map;


import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.domain.usecases.map.HelpUseCase;
import com.krestone.savealife.util.ConnectivityUtil;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class SosWindowPresenterImpl<T extends SosWindowPresenter.SosWindowView> implements SosWindowPresenter<T> {

    private T view;

    private HelpUseCase helpUseCase;

    public SosWindowPresenterImpl(HelpUseCase helpUseCase) {
        this.helpUseCase = helpUseCase;
    }

    @Override
    public void requestDesireToHelp(LatLng origin, LatLng dest, String number) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            helpUseCase.setPhoneNumber(number);
            helpUseCase.setEndpoints(origin, dest);
            requestDesireToHelp();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void requestDesireToHelp() {
        helpUseCase.executeSingle(routeModel -> {
            if (view != null) view.onHelpPressed(routeModel.getPolyline());
        }, throwable -> {
            if (view != null) view.onError(throwable.getLocalizedMessage());
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        helpUseCase.unsubscribe();
    }
}
