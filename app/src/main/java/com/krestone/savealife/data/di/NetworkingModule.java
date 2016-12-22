package com.krestone.savealife.data.di;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krestone.savealife.data.rest.ServerApi;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    private static final String BASE_URL = "https://www.google.com.ua/";

    private static final int READ_TIMEOUT = 10;

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(OkHttpClient client, Gson gson) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Singleton
    @Provides
    Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }


    @Singleton
    @Provides
    HttpLoggingInterceptor provideLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return interceptor;
    }

    @Singleton
    @Provides
    ServerApi provideserverApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }
}
