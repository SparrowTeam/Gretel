package com.tech.sparrow.gretel.API.models.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Denis on 25.11.2017.
 */

public class UserInfo implements Serializable{

    @SerializedName("name")
    private String name;

    @SerializedName("team")
    private Team team;

    public UserInfo(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", team=" + team +
                '}';
    }
}
