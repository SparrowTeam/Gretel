package com.tech.sparrow.gretel.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Denis on 25.11.2017.
 */

/**
 *  TODO this Class will be deleted after debug
 *
 */

public class Playground {

    private static String endpoint_url = "https://api.github.com/";
    private static HanselService hanselService;
    private Retrofit retrofit;

    public static void main(String[] args){
        new Playground().test();
    }

    public void test(){
        System.out.println("Hello world");
        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hanselService = retrofit.create(HanselService.class);
    }
}
