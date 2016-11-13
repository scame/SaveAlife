package com.krestone.savealife.domain.schedulers;


import rx.Scheduler;

public interface SubscribeOn {

    Scheduler getScheduler();
}
