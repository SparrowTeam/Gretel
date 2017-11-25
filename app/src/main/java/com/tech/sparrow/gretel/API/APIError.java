package com.tech.sparrow.gretel.API;

/**
 * Created by Denis on 25.11.2017.
 */

public class APIError {

    private int statusCode;

    private String message;

    public APIError() {
    }

    public int status() {
        return statusCode;
    }

    public String message() {
        return message;
    }
}
