package com.toshkin.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toshkin.popularmovies.PopularMoviesApplication;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.adapters.MoviesRecyclerAdapter;
import com.toshkin.popularmovies.network.API;
import com.toshkin.popularmovies.network.MoviesResponse;
import com.toshkin.popularmovies.network.MoviesSortOrder;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Lazar Toshkin
 */
public class MoviesGridFragment extends Fragment {
    public static final String TAG = "MoviesGridFragment.TAG";

    private API mApi;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;

    public static MoviesGridFragment newInstance() {
        return new MoviesGridFragment();
    }

    private Callback<MoviesResponse> mCallback = new Callback<MoviesResponse>() {
        @Override
        public void success(MoviesResponse moviesResponse, Response response) {

        }

        @Override
        public void failure(RetrofitError error) {

        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        configureRecyclerView();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getMovies(MoviesSortOrder.HIGHEST_RATED.toString(), mCallback);
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        mRecyclerView.setAdapter(new MoviesRecyclerAdapter());
    }
}
