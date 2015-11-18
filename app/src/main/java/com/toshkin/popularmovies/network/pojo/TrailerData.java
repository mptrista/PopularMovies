package com.toshkin.popularmovies.network.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lazar on 11/16/15.
 */
public class TrailerData implements Parcelable {
    @Expose
    @SerializedName("id")
    private String trailerId;
    @Expose
    @SerializedName("iso_639_1")
    private String isoStandard;
    @Expose
    @SerializedName("key")
    private String key;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("site")
    private String site;
    @Expose
    @SerializedName("size")
    private String size;
    @Expose
    @SerializedName("type")
    private String type;

    public String getName() {
        return name;
    }

    public String getTrailerId() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trailerId);
        dest.writeString(this.isoStandard);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeString(this.size);
        dest.writeString(this.type);
    }

    public TrailerData() {
    }

    protected TrailerData(Parcel in) {
        this.trailerId = in.readString();
        this.isoStandard = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<TrailerData> CREATOR = new Parcelable.Creator<TrailerData>() {
        public TrailerData createFromParcel(Parcel source) {
            return new TrailerData(source);
        }

        public TrailerData[] newArray(int size) {
            return new TrailerData[size];
        }
    };
}
