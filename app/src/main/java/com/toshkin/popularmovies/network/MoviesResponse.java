package com.toshkin.popularmovies.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.toshkin.popularmovies.pojos.MovieItem;

import java.util.List;

/**
 * @author Lazar Toshkin
 */
public class MoviesResponse {

    @Expose
    @SerializedName("page")
    private int page;

    @Expose
    @SerializedName("results")
    private List<MovieItem> movies;

    @Expose
    @SerializedName("total_pages")
    private int totalPages;

    @Expose
    @SerializedName("total_results")
    private int totalResults;

    public int getPage() {
        return page;
    }

    public List<MovieItem> getMovies() {
        return movies;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
