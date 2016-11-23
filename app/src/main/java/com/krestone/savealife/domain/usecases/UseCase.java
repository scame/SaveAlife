package com.krestone.savealife.domain.usecases;


import com.krestone.savealife.domain.schedulers.ObserveOn;
import com.krestone.savealife.domain.schedulers.SubscribeOn;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

public class UseCase<T> {

    protected SubscribeOn subscribeOn;
    protected ObserveOn observeOn;
    protected Subscription subscription = Subscriptions.empty();

    public UseCase(SubscribeOn subscribeOn, ObserveOn observeOn) {
        this.subscribeOn = subscribeOn;
        this.observeOn = observeOn;
    }

    public void unsubscribe() {
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }
}
