package com.toshkin.popularmovies.network;

import com.toshkin.popularmovies.network.response.MoviesResponse;
import com.toshkin.popularmovies.network.response.ReviewsResponse;
import com.toshkin.popularmovies.network.response.TrailerResponse;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * @author Lazar Toshkin
 */
public interface API {

    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sortType, Callback<MoviesResponse> callback);

    @GET("/movie/{id}/videos")
    void getTrailer(@Path("id") String movieId, Callback<TrailerResponse> callback);

    @GET("/movie/{id}/reviews")
    void getReviews(@Path("id") String movieId, Callback<ReviewsResponse> callback);

}
