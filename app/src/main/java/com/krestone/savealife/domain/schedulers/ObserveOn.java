package com.krestone.savealife.domain.schedulers;

import rx.Scheduler;

public interface ObserveOn {

    Scheduler getScheduler();
}
