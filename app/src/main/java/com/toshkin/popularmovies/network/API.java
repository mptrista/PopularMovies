package com.toshkin.popularmovies.network;

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
    void getTrailer(@Path("id") int movieId, Callback<TrailerResponse> callback);

    @GET("/movie/{id}/reviews")
    void getReviews(@Query("id") int movieId, Callback<ReviewsResponse> callback);

}
