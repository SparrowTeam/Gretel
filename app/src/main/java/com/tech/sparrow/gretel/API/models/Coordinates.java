package com.tech.sparrow.gretel.API.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Denis on 25.11.2017.
 */

public class Coordinates {

    @SerializedName("longtitude")
    private String longtitude;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("altitude")
    private String altitude;

    @SerializedName("code")
    private String code;

    public Coordinates(String longtitude, String latitude, String altitude, String code) {
        this.longtitude = longtitude;
        this.latitude = latitude;
        this.altitude = altitude;
        this.code = code;
    }

    public String getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(String longtitude) {
        this.longtitude = longtitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
