package com.toshkin.popularmovies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Lazar
 */
public class MovieItem {

    @Expose
    @SerializedName("")
    private String mAvatarUrl;

    public String getAvatarUrl() {
        return mAvatarUrl;
    }
}
