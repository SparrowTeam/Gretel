package com.tech.sparrow.gretel.API;

import com.tech.sparrow.gretel.API.models.request.LoginRequest;
import com.tech.sparrow.gretel.API.models.response.Token;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    private static String endpoint_url = "http://10.100.16.26:8080/";
    private static HanselService hanselService;
    private Retrofit retrofit;

    public static void main(String[] args){
        System.out.println("Hello world");
        Playground playground = new Playground();
        playground.onCreate();
        playground.testLogin("petya@pupkin.ru", "qwerty");
    }

    public void onCreate() {
        /**
         *  TODO move into Android application onCreate() method
         */
        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        hanselService = retrofit.create(HanselService.class);
    }

    public void testLogin(String email, String password){
        Call<Token> call = hanselService.login(new LoginRequest(email, password));
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()) {
                    Token tokenResponse = response.body();
                    System.out.println("Got token!!! "+tokenResponse.getToken());
                } else {
                    APIError error = ErrorUtils.parseError(retrofit, response);
                    System.out.println(error);
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                // there is more than just a failing request (like: no internet connection)
            }
        });
    }

    public void testInfo(String token){
        Call<UserInfo> call = hanselService.info(token);
        call.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.isSuccessful()) {
                    UserInfo userInfo = response.body();
                    System.out.println("Got user info!!! "+ userInfo);
                } else {
                    APIError error = ErrorUtils.parseError(retrofit, response);
                    System.out.println(error);
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                // there is more than just a failing request (like: no internet connection)
            }
        });
    }
}
