package com.tech.sparrow.gretel.API.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by Denis on 25.11.2017.
 */

public class MarkDetailedInfo {

    @SerializedName("id")
    private String rfid;

    @SerializedName("related_datetime")
    private RelatedDateTime relatedDateTime;

    @SerializedName("name")
    private String name;

    @SerializedName("team")
    private Team team;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    @SerializedName("value")
    private int value;

    @SerializedName("users")
    private List<User> users;

    @SerializedName("photos")
    private List<String> photos;

    @SerializedName("comments")
    private List<Comment> comments;

    public class RelatedDateTime {

        @SerializedName("registered")
        private Date registered;

        @SerializedName("updated")
        private Date updated;

        public Date getRegistered() {
            return registered;
        }

        public void setRegistered(Date registered) {
            this.registered = registered;
        }

        public Date getUpdated() {
            return updated;
        }

        public void setUpdated(Date updated) {
            this.updated = updated;
        }
    }

    public class User {

        @SerializedName("id")
        private int id;

        @SerializedName("name")
        private String name;

        @SerializedName("team_color")
        private String teamColor;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeamColor() {
            return teamColor;
        }

        public void setTeamColor(String teamColor) {
            this.teamColor = teamColor;
        }
    }

    public class Comment{

        @SerializedName("user_name")
        private String userName;

        @SerializedName("team_color")
        private String teamColor;

        @SerializedName("datetime")
        private Date datetime;

        @SerializedName("text")
        private String text;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getTeamColor() {
            return teamColor;
        }

        public void setTeamColor(String teamColor) {
            this.teamColor = teamColor;
        }

        public Date getDatetime() {
            return datetime;
        }

        public void setDatetime(Date datetime) {
            this.datetime = datetime;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
