package com.krestone.savealife.data.di;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.krestone.savealife.data.rest.MapboxApi;
import com.krestone.savealife.data.rest.ServerApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkingModule {

    private static final String BASE_URL = "https://www.google.com.ua/";

    private static final int READ_TIMEOUT = 10;

    @Singleton
    @Provides
    OkHttpClient provideOkHttp(HttpLoggingInterceptor loggingInterceptor,
                               @Named("requestLog") okhttp3.Interceptor requestLog) {
        return new OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(requestLog)
                .build();
    }

    private static String bodyToString(final Request request){

        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            if (copy.body() != null) {
                copy.body().writeTo(buffer);
                return buffer.readUtf8();
            } else {
                return "no body";
            }
        } catch (final IOException e) {
            return "did not work";
        }
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
    @Named("requestLog")
    okhttp3.Interceptor provideRequestLogInterceptor() {
        return chain -> {
            Request request = chain.request();
            Response response = chain.proceed(request);
            Log.i("onxRequestLog", bodyToString(request) + " " + request.url() +
                    " " + response.code() + " " + request.headers().toString());
            return response;
        };
    }


    @Singleton
    @Provides
    ServerApi provideServerApi(Retrofit retrofit) {
        return retrofit.create(ServerApi.class);
    }

    @Singleton
    @Provides
    MapboxApi provideMapboxApi(Retrofit retrofit) {
        return retrofit.create(MapboxApi.class);
    }
}
