package com.toshkin.popularmovies.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Lazar on 11/16/15.
 */
public class ReviewsResponse {

    @Expose
    @SerializedName("id")
    private int mResponseId;

    @Expose
    @SerializedName("page")
    private int mPageNumber;

    @Expose
    @SerializedName("results")
    private List<ReviewData> mReviewDatas;

    @Expose
    @SerializedName("total_results")
    private int mTotalReviews;

    @Expose
    @SerializedName("total_pages")
    private int mPageCount;

    public static class ReviewData {
        @Expose @SerializedName("id")
        private int reviewId;
        @Expose @SerializedName("author")
        private String authorName;
        @Expose @SerializedName("content")
        private String content;
        @Expose @SerializedName("url")
        private String url;

        public int getReviewId() {
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
    }

    public int getPageCount() {
        return mPageCount;
    }

    public int getTotalReviews() {
        return mTotalReviews;
    }

    public List<ReviewData> getReviewDatas() {
        return mReviewDatas;
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    public int getResponseId() {
        return mResponseId;
    }
}
