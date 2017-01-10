package com.krestone.savealife.data.rest;


import com.krestone.savealife.data.entities.responses.map.RoutesEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MapboxApi {

    @GET("https://api.mapbox.com/directions/v5/mapbox/{profile}/{coordinates}")
    Observable<RoutesEntity> getRoute(@Path("profile") String profile,
                                      @Path("coordinates") String coordinates,
                                      @Query("access_token") String accessToken);
}
