package com.krestone.savealife.presentation.presenters;


import com.krestone.savealife.data.entities.requests.SosEntity;
import com.krestone.savealife.domain.usecases.messages.StartSosUseCase;
import com.krestone.savealife.domain.usecases.messages.StopSosUseCase;

public class DashboardPresenterImpl<T extends DashboardPresenter.DashboardView> implements DashboardPresenter<T> {

    private T view;

    private StartSosUseCase startSosUseCase;

    private StopSosUseCase stopSosUseCase;

    public DashboardPresenterImpl(StartSosUseCase startSosUseCase, StopSosUseCase stopSosUseCase) {
        this.startSosUseCase = startSosUseCase;
        this.stopSosUseCase = stopSosUseCase;
    }

    @Override
    public void startSos() {
        startSosUseCase.setSosEntity(new SosEntity("", 0.1));
        startSosUseCase.executeCompletable(() -> {
            if (view != null) view.onStartSosCompleted();
        }, throwable -> {
            if (view != null) view.onError(throwable.toString());
        });
    }

    @Override
    public void stopSos() {
        stopSosUseCase.setSosEntity(new SosEntity("", 0.1));
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
