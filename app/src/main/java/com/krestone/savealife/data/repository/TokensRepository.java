package com.krestone.savealife.data.repository;


import rx.Completable;

public interface TokensRepository {

    Completable handleNewFirebaseToken(String firebaseToken);
}
