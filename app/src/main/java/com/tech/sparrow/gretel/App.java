package com.tech.sparrow.gretel;

import android.app.Application;

import com.tech.sparrow.gretel.API.HanselService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis on 25.11.2017.
 */

public class App extends Application {

    private static String endpoint_url = "http://10.100.16.26:8080/";
    private static HanselService hanselService;
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hanselService = retrofit.create(HanselService.class);
    }

    public static HanselService getApi() {
        return hanselService;
    }

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}
