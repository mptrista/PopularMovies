package com.toshkin.popularmovies.network;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * @author Lazar Toshkin
 */
public interface API {

    @GET("/discover/movie")
    void getMovies(Callback<MoviesResponse> callback);

}
