package com.toshkin.popularmovies.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

    public static class TrailerData {
        @Expose @SerializedName("id")
        private int trailerId;
        @Expose @SerializedName("iso_639_1")
        private String isoStandard;
        @Expose @SerializedName("key")
        private String key;
        @Expose @SerializedName("name")
        private String name;
        @Expose @SerializedName("site")
        private String site;
        @Expose @SerializedName("size")
        private String size;
        @Expose @SerializedName("type")
        private String type;

        public String getName() {
            return name;
        }

        public int getTrailerId() {
            return trailerId;
        }

        public String getIsoStandard() {
            return isoStandard;
        }

        public String getKey() {
            return key;
        }

        public String getSite() {
            return site;
        }

        public String getSize() {
            return size;
        }

        public String getType() {
            return type;
        }
    }


    public int getResponseId() {
        return mResponseId;
    }

    public List<TrailerData> getTrailerDatas() {
        return mTrailerDatas;
    }
}
