package com.krestone.savealife.presentation.presenters;


import android.util.Log;

import com.krestone.savealife.R;
import com.krestone.savealife.SaveAlifeApplication;
import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.data.entities.requests.UpdateMyProfileInfoRequest;
import com.krestone.savealife.data.entities.responses.MyProfileInfoEntity;
import com.krestone.savealife.domain.usecases.messages.StartSosUseCase;
import com.krestone.savealife.domain.usecases.messages.StopSosUseCase;
import com.krestone.savealife.domain.usecases.profiles.GetMyProfileInfoUseCase;
import com.krestone.savealife.domain.usecases.profiles.UpdateMyProfileInfoUseCase;
import com.krestone.savealife.util.ConnectivityUtil;

public class DashboardPresenterImpl<T extends DashboardPresenter.DashboardView> implements DashboardPresenter<T> {

    private T view;

    private StartSosUseCase startSosUseCase;

    private StopSosUseCase stopSosUseCase;

    private GetMyProfileInfoUseCase getMyProfileInfoUseCase;

    private UpdateMyProfileInfoUseCase updateMyProfileInfoUseCase;

    public DashboardPresenterImpl(StartSosUseCase startSosUseCase, StopSosUseCase stopSosUseCase,
                                  GetMyProfileInfoUseCase getMyProfileInfoUseCase,
                                  UpdateMyProfileInfoUseCase updateMyProfileInfoUseCase) {
        this.startSosUseCase = startSosUseCase;
        this.stopSosUseCase = stopSosUseCase;
        this.getMyProfileInfoUseCase = getMyProfileInfoUseCase;
        this.updateMyProfileInfoUseCase = updateMyProfileInfoUseCase;
    }


    @Override
    public void updateMyProfileInfo(boolean isDriver) {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            MyProfileInfoEntity profileInfo = new MyProfileInfoEntity();
            if (isDriver) {
                profileInfo.setRole("driver");
            } else {
                profileInfo.setRole("person");
            }
            UpdateMyProfileInfoRequest request = new UpdateMyProfileInfoRequest(profileInfo);
            updateMyProfileInfoUseCase.setUpdateMyProfileInfoRequest(request);
            updateMyProfileInfo();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void updateMyProfileInfo() {
        updateMyProfileInfoUseCase.executeCompletable(() -> {
            if (view != null) Log.i("onxStatusChanged", "ok");
        }, throwable -> {
            if (view != null) view.onError(throwable.toString());
        });
    }

    @Override
    public void getMyProfileInfo() {
        getMyProfileInfoUseCase.executeSingle(profileInfo -> {
            if (view != null) {
                if (profileInfo.getRole().equals("driver")) {
                    view.displayIsDriverState(true);
                } else if (profileInfo.getRole().equals("person")) {
                    view.displayIsDriverState(false);
                }
            }
        }, throwable -> {
            if (view != null) view.onError(throwable.getLocalizedMessage());
        });
    }

    @Override
    public void startSos() {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            startSosUseCase.setSosEntity(new SosEntity("", 0.1));
            startSosCompletable();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void startSosCompletable() {
        startSosUseCase.executeCompletable(() -> {
            if (view != null) view.onStartSosCompleted();
        }, throwable -> {
            if (view != null) view.onError(throwable.toString());
        });
    }

    @Override
    public void stopSos() {
        if (ConnectivityUtil.isNetworkOn(SaveAlifeApplication.application)) {
            stopSosUseCase.setSosEntity(new SosEntity("", 0.1));
            stopSosCompletable();
        } else if (view != null) {
            view.onError(SaveAlifeApplication.application.getString(R.string.internet_connection_check));
        }
    }

    private void stopSosCompletable() {
        stopSosUseCase.executeCompletable(() -> {
            if (view != null) view.onStopSosCompleted();
        }, throwable -> {
            if (view != null) view.onError(throwable.toString());
        });
    }

    @Override
    public void setView(T view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
        startSosUseCase.unsubscribe();
        stopSosUseCase.unsubscribe();
    }
}
