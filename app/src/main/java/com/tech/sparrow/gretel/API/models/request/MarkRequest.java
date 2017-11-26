package com.tech.sparrow.gretel.API.models.request;

import com.google.gson.annotations.SerializedName;
import com.tech.sparrow.gretel.API.models.response.Coordinates;
import com.tech.sparrow.gretel.API.models.response.ImageInfo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by tako on 26.11.17.
 */

public class MarkRequest implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("coordinates")
    private Coordinates coordinates;

    @SerializedName("photos")
    private ArrayList<ImageInfo> photos;

    public MarkRequest(String name, Coordinates coordinates, ArrayList<ImageInfo> photos) {
        this.name = name;
        this.coordinates = coordinates;
        this.photos = photos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ArrayList<ImageInfo> getPhotos() { return photos; }

    public void setPhotos(ArrayList<ImageInfo> photos) { this.photos = photos; }
}
