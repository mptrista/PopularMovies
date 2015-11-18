package com.toshkin.popularmovies.network.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lazar
 */
public class MovieItem implements Parcelable {

    @Expose
    @SerializedName("adult")
    private boolean isAdult;
    @Expose
    @SerializedName("backdrop_path")
    private String backdropPath;
    @Expose
    @SerializedName("genre_ids")
    private List<Integer> genreIds;
    @Expose
    @SerializedName("id")
    private String movieId;
    @Expose
    @SerializedName("original_language")
    private String language;
    @Expose
    @SerializedName("original_title")
    private String originalTitle;
    @Expose
    @SerializedName("overview")
    private String overview;
    @Expose
    @SerializedName("release_date")
    private String releaseDate;
    @Expose
    @SerializedName("poster_path")
    private String posterPath;
    @Expose
    @SerializedName("popularity")
    private float popularity;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("video")
    private boolean video;
    @Expose
    @SerializedName("vote_average")
    private float averageVote;
    @Expose
    @SerializedName("vote_count")
    private int voteCount;
    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        this.isAdult = in.readByte() != 0;
        this.backdropPath = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, List.class.getClassLoader());
        this.movieId = in.readString();
        this.language = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.posterPath = in.readString();
        this.popularity = in.readFloat();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.averageVote = in.readFloat();
        this.voteCount = in.readInt();
    }

    public boolean isAdult() {
        return isAdult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getLanguage() {
        return language;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public float getAverageVote() {
        return averageVote;
    }

    public int getVoteCount() {
        return voteCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(isAdult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdropPath);
        dest.writeList(this.genreIds);
        dest.writeString(this.movieId);
        dest.writeString(this.language);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.posterPath);
        dest.writeFloat(this.popularity);
        dest.writeString(this.title);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeFloat(this.averageVote);
        dest.writeInt(this.voteCount);
    }
}
