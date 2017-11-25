package com.tech.sparrow.gretel.API;

import com.tech.sparrow.gretel.API.models.request.LoginRequest;
import com.tech.sparrow.gretel.API.models.request.RegisterRequest;
import com.tech.sparrow.gretel.API.models.response.MarkByUserId;
import com.tech.sparrow.gretel.API.models.response.Token;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Denis on 25.11.2017.
 */

public interface HanselService {

    @Headers("Content-Type: application/json")
    @POST("user/login")
    Call<Token> login(@Body LoginRequest loginRequest);

    @Headers("Content-Type: application/json")
    @POST("user/register")
    Call<Token> register(@Body RegisterRequest registerRequest);

    @GET("user/info")
    Call<UserInfo> info(@Header("X-API-TOKEN") String X_API_TOKEN);

    @GET("user/marks/{user_id}")
    Call<List<MarkByUserId>> listMarksByUserId(@Header("X-API-TOKEN") String X_API_TOKEN, @Path("user_id") String user_id);
}
