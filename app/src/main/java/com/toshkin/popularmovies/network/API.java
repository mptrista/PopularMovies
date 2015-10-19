package com.toshkin.popularmovies.network;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * @author Lazar Toshkin
 */
public interface API {

    @GET("/discover/movie")
    void getMovies(@Query("sort_by") String sortType, Callback<MoviesResponse> callback);

}
