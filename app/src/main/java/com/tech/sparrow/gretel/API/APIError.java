package com.tech.sparrow.gretel.API;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis on 25.11.2017.
 */

public class APIError {

    private int statusCode;

    @SerializedName("error")
    private String error;

    public APIError() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "APIError{" +
                "statusCode=" + statusCode +
                ", error='" + error + '\'' +
                '}';
    }
}
