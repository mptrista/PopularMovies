package com.toshkin.popularmovies.network;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;

/**
 * @author Lazar Toshkin
 */
public interface API {

    @FormUrlEncoded
    @GET("/discover/movie")
    void getMovies(@Field("sort_by") String sortType, Callback callback);

}
