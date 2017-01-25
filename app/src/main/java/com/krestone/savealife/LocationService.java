package com.krestone.savealife;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.domain.usecases.base.DefaultSubscriber;

import javax.inject.Inject;

import rx.Completable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

// FIXME: 1/25/17 if app process crash then we could get inconsistent static variable, should be read from prefs
public class LocationService extends Service {

    @Inject
    LocationRepository locationRepository;

    private static CompositeSubscription compositeSubscription = new CompositeSubscription();

    private static boolean shouldGettingLocationUpdates = true;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveAlifeApplication.getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (shouldGettingLocationUpdates) {
            compositeSubscription.add(locationRepository
                    .startGettingLocationUpdates().subscribe(new LocationSubscriber()));
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        unsubscribe();
        super.onDestroy();
    }

    private final class LocationSubscriber extends DefaultSubscriber<Location> {

        @Override
        public void onNext(Location location) {
            super.onNext(location);
            locationRepository.sendLocationToServer(new LocationHolder(location.getLatitude(), location.getLongitude()))
                    .retry(3)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(responseBody -> Log.i("onxNext", "sent"),
                            throwable -> {
                                if (throwable != null) Log.i("onxLocationErr", throwable.getLocalizedMessage());
                            }
                    );
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.i("onxLocationServiceErr", e.getLocalizedMessage());
        }
    }

    public static Completable startGettingLocationUpdates(Context context) {
        shouldGettingLocationUpdates = true;
        Context appContext = context.getApplicationContext();
        appContext.startService(new Intent(appContext, LocationService.class));

        return Completable.complete();
    }

    public static Completable stopGettingLocationUpdates() {
        shouldGettingLocationUpdates = false;

        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
            compositeSubscription = new CompositeSubscription();
        }
        return Completable.complete();
    }

    private static void unsubscribe() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
            compositeSubscription = new CompositeSubscription();
        }
    }
}
