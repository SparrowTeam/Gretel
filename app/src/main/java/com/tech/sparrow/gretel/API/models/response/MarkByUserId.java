package com.tech.sparrow.gretel.API.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Denis on 25.11.2017.
 */

public class MarkByUserId {

    @SerializedName("rfid")
    private String rfid;

    @SerializedName("name")
    private String name;

    @SerializedName("updated_datetime")
    private Date updated_datetime;

    @SerializedName("value")
    private int value;

    @SerializedName("team_id")
    private int team_id;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    public MarkByUserId(String rfid, String name, Date updated_datetime, int value, int team_id, Coordinates coordinates) {
        this.rfid = rfid;
        this.name = name;
        this.updated_datetime = updated_datetime;
        this.value = value;
        this.team_id = team_id;
        this.coordinates = coordinates;
    }

    public String getRfid() {
        return rfid;
    }

    public void setRfid(String rfid) {
        this.rfid = rfid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getUpdated_datetime() {
        return updated_datetime;
    }

    public void setUpdated_datetime(Date updated_datetime) {
        this.updated_datetime = updated_datetime;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
