package com.tech.sparrow.gretel.API;

import com.tech.sparrow.gretel.API.models.request.LoginRequest;
import com.tech.sparrow.gretel.API.models.request.RegisterRequest;
import com.tech.sparrow.gretel.API.models.response.MarkInfo;
import com.tech.sparrow.gretel.API.models.response.MarkDetailedInfo;
import com.tech.sparrow.gretel.API.models.response.Token;
import com.tech.sparrow.gretel.API.models.response.UserInfo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<List<MarkInfo>> listMarksByUserId(@Header("X-API-TOKEN") String X_API_TOKEN, @Path("user_id") String user_id);

    @GET("marks/{mark_id}")
    Call<MarkDetailedInfo> getMarkInfo(@Header("X-API-TOKEN") String X_API_TOKEN, @Path("mark_id") String mark_id);

    @GET("mark/{mark_id}/status")
    Call<ResponseBody> getMarkStatus(@Header("X-API-TOKEN") String X_API_TOKEN, @Path("mark_id") String mark_id);


    @Multipart
    @POST("/photo")
    Call<ResponseBody> postImage(@Header("X-API-TOKEN") String X_API_TOKEN, @Part MultipartBody.Part image, @Part("name") RequestBody name);
}
