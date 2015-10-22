package com.toshkin.popularmovies.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.toshkin.popularmovies.PopularMoviesApplication;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.adapters.MoviesRecyclerAdapter;
import com.toshkin.popularmovies.interfaces.MovieSelectedListener;
import com.toshkin.popularmovies.interfaces.NavigationProvider;
import com.toshkin.popularmovies.network.API;
import com.toshkin.popularmovies.network.MoviesResponse;
import com.toshkin.popularmovies.network.MoviesSortOrder;
import com.toshkin.popularmovies.pojos.MovieItem;

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
    private MoviesRecyclerAdapter mAdapter;
    private NavigationProvider mNavigator;
    private Toolbar mToolbar;

    public static MoviesGridFragment newInstance() {
        return new MoviesGridFragment();
    }

    final private MovieSelectedListener mMovieListener = new MovieSelectedListener() {
        @Override
        public void onMovieSelected(MovieItem item) {
            if (mNavigator != null) {
                mNavigator.openMovieDetailFragment(item);
            }
        }
    };

    private Callback<MoviesResponse> mCallback = new Callback<MoviesResponse>() {
        @Override
        public void success(MoviesResponse moviesResponse, Response response) {
            mAdapter.addItems(moviesResponse.getMovies());
        }

        @Override
        public void failure(RetrofitError error) {
            Toast.makeText(getContext(), "Retrieving info failed!", Toast.LENGTH_SHORT).show();
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        mAdapter = new MoviesRecyclerAdapter();
        configureRecyclerView();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getHost() instanceof NavigationProvider) {
            mNavigator = (NavigationProvider) getHost();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mNavigator = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getMovies(MoviesSortOrder.MOST_POPULAR.toString(), mCallback);
        mAdapter.setMovieListener(mMovieListener);
    }

    @Override
    public void onPause() {
        mAdapter.setMovieListener(null);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView = null;
        mToolbar = null;
    }

    private void configureRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }
}
