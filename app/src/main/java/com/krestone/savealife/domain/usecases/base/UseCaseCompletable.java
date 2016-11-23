package com.krestone.savealife.domain.usecases.base;


import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;

import rx.Completable;
import rx.functions.Action0;
import rx.functions.Action1;

public abstract class UseCaseCompletable<T> extends UseCase<T> {

    private Completable completable;

    public UseCaseCompletable(SubscribeOn subscribeOn, ObserveOn observeOn) {
        super(subscribeOn, observeOn);
    }

    public void executeCompletable(Action0 onComplete, Action1<? super Throwable> onError) {
        if (completable == null) {
            completable = getUseCaseCompletable()
                    .subscribeOn(subscribeOn.getScheduler())
                    .observeOn(observeOn.getScheduler())
                    .doOnError((t) -> completable = null)
                    .doOnCompleted(() -> completable = null);
        }
        subscription = completable.subscribe(onComplete, onError);
    }

    protected abstract Completable getUseCaseCompletable();
}
