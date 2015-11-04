package com.toshkin.popularmovies.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
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
import com.toshkin.popularmovies.pojos.MovieItem;
import com.toshkin.popularmovies.utils.Constants;
import com.toshkin.popularmovies.utils.DividerItemDecoration;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Lazar Toshkin
 */
public class MoviesGridFragment extends Fragment implements OnSharedPreferenceChangeListener {
    public static final String TAG = "MoviesGridFragment.TAG";

    public static final String KEY_ADAPTER_STATE = "ADAPTER_STATE";
    private static final String KEY_LAYOUT_MANAGER_STATE = "LAYOUT_STATE";

    private API mApi;
    private RecyclerView mRecyclerView;
    private MoviesRecyclerAdapter mAdapter;
    private NavigationProvider mNavigator;
    final private MovieSelectedListener mMovieListener = new MovieSelectedListener() {
        @Override
        public void onMovieSelected(MovieItem item) {
            if (mNavigator != null) {
                mNavigator.openMovieDetailFragment(item);
            }
        }
    };
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;
    private SharedPreferences mSharedPreferences;
    private Callback<MoviesResponse> mCallback = new Callback<MoviesResponse>() {
        @Override
        public void success(MoviesResponse moviesResponse, Response response) {
            mAdapter.clear();
            mAdapter.addItems(moviesResponse.getMovies());
        }

        @Override
        public void failure(RetrofitError error) {
            showError();
        }
    };

    public static MoviesGridFragment newInstance() {
        return new MoviesGridFragment();
    }

    private static int getThemeAttribute(Resources.Theme theme, int themeAttr) {
        final TypedValue value = new TypedValue();
        theme.resolveAttribute(themeAttr, value, true);
        return value.resourceId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPreferences = getSharedPrefs();
        mAdapter = new MoviesRecyclerAdapter();
        if (savedInstanceState != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_ADAPTER_STATE));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_grid, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        configureRecyclerView(savedInstanceState);
        configureToolbar();
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
        mAdapter.setMovieListener(mMovieListener);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        if (mAdapter.getItemCount() == 0) {
            String order = getSharedPrefs().getString(Constants.PREFS_ORDERING, Constants.ORDER_POPULAR_DESC);
            requestMovies(order);
        }
    }

    @Override
    public void onPause() {
        mAdapter.setMovieListener(null);
        mSharedPreferences.registerOnSharedPreferenceChangeListener(null);
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mRecyclerView = null;
        mToolbar = null;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (Constants.PREFS_ORDERING.equals(key)) {
            String order = getSharedPrefs().getString(Constants.PREFS_ORDERING, null);
            if (order != null) {
                requestMovies(order);
            } else {
                showError();
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ADAPTER_STATE, mAdapter.onSaveInstanceState());
        if (mRecyclerView != null) {
            outState.putParcelable(KEY_LAYOUT_MANAGER_STATE, mLayoutManager.onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_ADAPTER_STATE));
        }
    }

    private void requestMovies(@NonNull String ordering) {
        mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getMovies(ordering, mCallback);
    }

    private void configureRecyclerView(Bundle savedInstanceState) {
        mLayoutManager = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        if (savedInstanceState != null) {
            mLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_LAYOUT_MANAGER_STATE));
        }
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Context context = mRecyclerView.getContext();
        int dividerDrawableRes = getThemeAttribute(context.getTheme(), R.attr.dividerVertical);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(context, dividerDrawableRes, DividerItemDecoration.VERTICAL_LIST));
    }

    private void showError() {
        Toast.makeText(getContext(), "Retrieving info failed! Check if you are online!", Toast.LENGTH_SHORT).show();
    }

    private void configureToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.inflateMenu(R.menu.menu_main);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_settings && mNavigator != null) {
                    mNavigator.openSettingsDialog();
                }
                return false;
            }
        });
    }

    private SharedPreferences getSharedPrefs() {
        if (mSharedPreferences != null) {
            return mSharedPreferences;
        } else {
            Context context = getActivity();
            mSharedPreferences = context.getSharedPreferences(Constants.PREFS_FILE, Context.MODE_PRIVATE);
            return mSharedPreferences;
        }
    }
}
