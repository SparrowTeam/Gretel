package com.tech.sparrow.gretel.API;

import com.tech.sparrow.gretel.API.models.MarkByUserId;
import com.tech.sparrow.gretel.API.models.TokenResponse;
import com.tech.sparrow.gretel.API.models.UserInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Denis on 25.11.2017.
 */

public interface HanselService {

    @FormUrlEncoded
    @POST("user/login")
    Call<TokenResponse> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<TokenResponse> register(@Field("email") String email, @Field("password") String password, @Field("name") String name);

    @GET("user/info")
    Call<UserInfo> info(@Header("X-API-TOKEN") String X_API_TOKEN);

    @GET("user/marks/{user_id}")
    Call<List<MarkByUserId>> listMarksByUserId(@Header("X-API-TOKEN") String X_API_TOKEN, @Path("user_id") String user_id);
}
