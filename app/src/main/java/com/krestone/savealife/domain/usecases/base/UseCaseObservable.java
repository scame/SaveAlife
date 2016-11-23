package com.krestone.savealife.domain.usecases.base;


import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;

import rx.Observable;
import rx.Subscriber;

public abstract class UseCaseObservable<T> extends UseCase<T> {

    private Observable<T> observable;

    public UseCaseObservable(SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void executeObservable(Subscriber<T> subscriber) {
        if (observable == null) {
            observable = getUseCaseObservable()
                    .subscribeOn(subscribeOn.getScheduler())
                    .observeOn(observeOn.getScheduler())
                    .cache()
                    .doOnError((t) -> observable = null)
                    .doOnCompleted(() -> observable = null);
        }
        subscription = observable.subscribe(subscriber);
    }

    protected abstract Observable<T> getUseCaseObservable();
}
