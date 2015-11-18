package com.toshkin.popularmovies.network.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.toshkin.popularmovies.network.pojo.TrailerData;

import java.util.List;

/**
 * Created by Lazar on 11/16/15.
 */
public class TrailerResponse {
    @Expose
    @SerializedName("id")
    private int mResponseId;

    @Expose
    @SerializedName("results")
    private List<TrailerData> mTrailerDatas;


    public int getResponseId() {
        return mResponseId;
    }

    public List<TrailerData> getTrailerDatas() {
        return mTrailerDatas;
    }
}
