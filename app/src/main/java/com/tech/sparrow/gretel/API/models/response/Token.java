package com.tech.sparrow.gretel.API.models.response;

/**
 * Created by Denis on 25.11.2017.
 */

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    private String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
