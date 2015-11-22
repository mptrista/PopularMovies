package com.toshkin.popularmovies;

import android.app.Application;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.toshkin.popularmovies.network.API;
import com.toshkin.popularmovies.network.ApiKeyInterceptor;
import com.toshkin.popularmovies.network.ServerDateDeserializer;

import java.util.Date;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * @author Lazar
 */
public class PopularMoviesApplication extends Application {

    private static String API_ENDPOINT = "http://api.themoviedb.org/3";

    private static PopularMoviesApplication instance;

    public PopularMoviesApplication() {
        instance = this;
    }

    public static PopularMoviesApplication getInstance(){
        return instance;
    }

    private API mApi;

    public API getAPI() {
        if (mApi != null) {
            return mApi;
        } else {
            return this.setupRestClient();
        }
    }

    private API setupRestClient() {
        RestAdapter.Builder builder = new RestAdapter.Builder()
                .setEndpoint(API_ENDPOINT)
                .setRequestInterceptor(new ApiKeyInterceptor(getApplicationContext()))
                .setClient(new OkClient(new OkHttpClient()))
                .setConverter(new GsonConverter(setupGson()))
                .setLogLevel(RestAdapter.LogLevel.FULL);

        RestAdapter restAdapter = builder.build();
        mApi = restAdapter.create(API.class);
        return mApi;
    }

    private Gson setupGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .registerTypeAdapter(Date.class, new ServerDateDeserializer())
                .excludeFieldsWithoutExposeAnnotation()
                .create();
    }
}
