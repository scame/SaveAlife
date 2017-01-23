package com.krestone.savealife;


import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.krestone.savealife.data.entities.requests.LocationHolder;
import com.krestone.savealife.data.repository.LocationRepository;
import com.krestone.savealife.domain.usecases.base.DefaultSubscriber;

import javax.inject.Inject;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LocationService extends Service {

    @Inject
    LocationRepository locationRepository;

    private Subscription subscription;

    @Override
    public void onCreate() {
        super.onCreate();
        SaveAlifeApplication.getAppComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        subscription = locationRepository.startGettingLocationUpdates().subscribe(new LocationSubscriber());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private final class LocationSubscriber extends DefaultSubscriber<Location> {

        @Override
        public void onNext(Location location) {
            super.onNext(location);
            locationRepository.sendLocationToServer(new LocationHolder(location.getLatitude(), location.getLongitude()))
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
}
