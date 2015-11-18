package com.toshkin.popularmovies.network.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Lazar on 11/16/15.
 */
public class ReviewData implements Parcelable {
    @Expose
    @SerializedName("id")
    private String reviewId;
    @Expose
    @SerializedName("author")
    private String authorName;
    @Expose
    @SerializedName("content")
    private String content;
    @Expose
    @SerializedName("url")
    private String url;

    public String getReviewId() {
        return reviewId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getContent() {
        return content;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.reviewId);
        dest.writeString(this.authorName);
        dest.writeString(this.content);
        dest.writeString(this.url);
    }

    public ReviewData() {
    }

    protected ReviewData(Parcel in) {
        this.reviewId = in.readString();
        this.authorName = in.readString();
        this.content = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<ReviewData> CREATOR = new Parcelable.Creator<ReviewData>() {
        public ReviewData createFromParcel(Parcel source) {
            return new ReviewData(source);
        }

        public ReviewData[] newArray(int size) {
            return new ReviewData[size];
        }
    };
}
