package com.tech.sparrow.gretel.API;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Denis on 25.11.2017.
 */

public class ErrorUtils {

    public static APIError parseError(Retrofit retrofit, Response<?> response) {
        Converter<ResponseBody, APIError> converter = retrofit.responseBodyConverter(APIError.class, new Annotation[0]);

        APIError error;

        try {
            error = converter.convert(response.errorBody());
            error.setStatusCode(response.code());
        } catch (IOException e) {
            return new APIError();
        }

        return error;
    }
}
