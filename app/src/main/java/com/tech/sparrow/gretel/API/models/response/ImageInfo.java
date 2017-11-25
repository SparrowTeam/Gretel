package com.tech.sparrow.gretel.API.models.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by tako on 26.11.17.
 */

public class ImageInfo implements Serializable {
    @SerializedName("image_id")
    private  String imageId;

    public ImageInfo(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return imageId;
    }
}
