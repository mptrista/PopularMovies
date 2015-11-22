package com.toshkin.popularmovies.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.toshkin.popularmovies.PopularMoviesApplication;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.adapters.ReviewsRecyclerAdapter;
import com.toshkin.popularmovies.adapters.TrailersRecyclerAdapter;
import com.toshkin.popularmovies.interfaces.ItemSelectedListener;
import com.toshkin.popularmovies.network.API;
import com.toshkin.popularmovies.network.pojo.MovieItem;
import com.toshkin.popularmovies.network.pojo.TrailerData;
import com.toshkin.popularmovies.network.response.ReviewsResponse;
import com.toshkin.popularmovies.network.response.TrailerResponse;
import com.toshkin.popularmovies.utils.Constants;
import com.toshkin.popularmovies.utils.DividerItemDecoration;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author Lazar
 */
public class MovieDetailFragment extends Fragment {
    public static final String TAG = "MovieDetailFragment.TAG";
    public static final String ARG_MOVIE_ITEM = "ARG_MOVIE_ITEM";
    public static final String YOUTUBE = "YouTube";

    public static final String KEY_TRAILER_ADAPTER_STATE = "Trailer.ADAPTER_STATE";
    public static final String KEY_REVIEWS_ADAPTER_STATE = "Reviews.ADAPTER_STATE";
    private static final String KEY_TRAILER_LAYOUT_MANAGER_STATE = "Trailer.LAYOUT_STATE";
    private static final String KEY_REVIEWS_LAYOUT_MANAGER_STATE = "Reviews.LAYOUT_STATE";

    private MovieItem mMovieItem;

    private ImageView mPosterView;
    private TextView mTitleTextView;
    private TextView mRatingTextView;
    private Button mFavoriteButton;
    private TextView mSummaryTextView;
    private Toolbar mToolbar;

    private ReviewsRecyclerAdapter mReviewsAdapter;
    private TrailersRecyclerAdapter mTrailerAdapter;
    private RecyclerView mTrailerRecyclerView;
    private RecyclerView mReviewsRecyclerView;
    private RecyclerView.LayoutManager mTrailerLayoutManager;
    private RecyclerView.LayoutManager mReviewsLayoutManager;

    final private ItemSelectedListener mTrailerListener = new ItemSelectedListener() {
        @Override
        public void onItemSelected(int position) {
            TrailerData trailerData = mTrailerAdapter.getItems().get(position);
            if (YOUTUBE.equals(trailerData.getSite())) {
                startVideo(trailerData.getKey());
            }
        }
    };

    public static MovieDetailFragment newInstance(MovieItem item) {
        if (item == null) {
            throw new NullPointerException("Null movie provided");
        }
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_ITEM, item);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieItem = getArguments().getParcelable(ARG_MOVIE_ITEM);

        mTrailerAdapter = new TrailersRecyclerAdapter();
        if (savedInstanceState != null) {
            mTrailerAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_TRAILER_ADAPTER_STATE));
        }

        mReviewsAdapter = new ReviewsRecyclerAdapter(getContext());
        if (savedInstanceState != null) {
            mReviewsAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_REVIEWS_ADAPTER_STATE));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mTrailerAdapter.setListener(mTrailerListener);
        if (TextUtils.isEmpty(mMovieItem.getTitle())) {
            requestMovie();
        }
        if (mTrailerAdapter.getItemCount() == 0) {
            requestTrailers();
        }
        if (mReviewsAdapter.getItemCount() == 0) {
            requestReviews();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mTrailerAdapter.setListener(null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mTrailerRecyclerView = (RecyclerView) rootView.findViewById(R.id.trailer_recycler_view);
        mReviewsRecyclerView = (RecyclerView) rootView.findViewById(R.id.reviews_recycler_view);
        mSummaryTextView = (TextView) rootView.findViewById(R.id.summary_text_view);
        mRatingTextView = (TextView) rootView.findViewById(R.id.rating_text_view);
        mPosterView = (ImageView) rootView.findViewById(R.id.poster_image_view);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        mFavoriteButton = (Button) rootView.findViewById(R.id.favorite_button);
        mToolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        configureTrailerRecyclerView(savedInstanceState);
        configureReviewRecyclerView(savedInstanceState);
        return rootView;
    }

    private void configureTrailerRecyclerView(Bundle savedInstanceState) {
        mTrailerLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        if (savedInstanceState != null) {
            mTrailerLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_TRAILER_LAYOUT_MANAGER_STATE));
        }
        mTrailerRecyclerView.setHasFixedSize(true);
        mTrailerRecyclerView.setLayoutManager(mTrailerLayoutManager);
        mTrailerRecyclerView.setAdapter(mTrailerAdapter);
        mTrailerRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Context context = mTrailerRecyclerView.getContext();
        int dividerDrawableRes = getThemeAttribute(context.getTheme(), R.attr.dividerHorizontal);
        mTrailerRecyclerView.addItemDecoration(new DividerItemDecoration(context, dividerDrawableRes, DividerItemDecoration.HORIZONTAL_LIST));
    }

    private void configureReviewRecyclerView(Bundle savedInstanceState) {
        mReviewsLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        if (savedInstanceState != null) {
            mTrailerLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_REVIEWS_LAYOUT_MANAGER_STATE));
        }
        mReviewsRecyclerView.setLayoutManager(mReviewsLayoutManager);
        mReviewsRecyclerView.setAdapter(mReviewsAdapter);
        mReviewsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        Context context = mReviewsRecyclerView.getContext();
        int dividerDrawableRes = getThemeAttribute(context.getTheme(), R.attr.dividerVertical);
        mReviewsRecyclerView.addItemDecoration(new DividerItemDecoration(context, dividerDrawableRes, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mToolbar = null;
        mPosterView = null;
        mTitleTextView = null;
        mRatingTextView = null;
        mFavoriteButton = null;
        mSummaryTextView = null;
        mReviewsRecyclerView = null;
        mTrailerRecyclerView = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureTextViews();
        Picasso.with(getContext())
                .load(Constants.POSTER_BASE_URL + mMovieItem.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .centerCrop()
                .fit()
                .into(mPosterView);
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!movieIsFavorite()) {
                    getSharedPrefs().edit().putString(mMovieItem.getMovieId(), mMovieItem.getPosterPath()).commit();
                    Toast.makeText(getContext(), mMovieItem.getTitle() + " added to favorites!", Toast.LENGTH_SHORT).show();
                    mFavoriteButton.setText("Remove favorites");
                } else {
                    getSharedPrefs().edit().remove(mMovieItem.getMovieId()).commit();
                    Toast.makeText(getContext(), mMovieItem.getTitle() + " removed from favorites!", Toast.LENGTH_SHORT).show();
                    mFavoriteButton.setText("Add favorites");
                }
            }
        });
        configureFavoriteButton();
    }

    private void configureFavoriteButton() {
        if (movieIsFavorite()) {
            mFavoriteButton.setText("Remove favorites");
        } else {
            mFavoriteButton.setText("Add favorites");
        }
    }

    private boolean movieIsFavorite() {
        String moviePoster = getSharedPrefs().getString(mMovieItem.getMovieId(), null);
        return !TextUtils.isEmpty(moviePoster);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_TRAILER_ADAPTER_STATE, mTrailerAdapter.onSaveInstanceState());
        outState.putParcelable(KEY_REVIEWS_ADAPTER_STATE, mReviewsAdapter.onSaveInstanceState());
        if (mTrailerRecyclerView != null) {
            outState.putParcelable(KEY_TRAILER_LAYOUT_MANAGER_STATE, mTrailerLayoutManager.onSaveInstanceState());
        }
        if (mReviewsRecyclerView != null) {
            outState.putParcelable(KEY_REVIEWS_LAYOUT_MANAGER_STATE, mReviewsLayoutManager.onSaveInstanceState());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            mTrailerAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_TRAILER_ADAPTER_STATE));
            mReviewsAdapter.onRestoreInstanceState(savedInstanceState.getParcelable(KEY_REVIEWS_ADAPTER_STATE));
        }
    }

    private Callback<MovieItem> mCallback = new Callback<MovieItem>() {
        @Override
        public void success(MovieItem movieItem, Response response) {
            if (MovieDetailFragment.this.isResumed()) {
                mMovieItem = movieItem;
                configureTextViews();
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (MovieDetailFragment.this.isResumed()) {
                showError("details");
            }
        }
    };

    private Callback<TrailerResponse> mTrailersCallback = new Callback<TrailerResponse>() {
        @Override
        public void success(TrailerResponse trailerResponse, Response response) {
            if (MovieDetailFragment.this.isResumed() && trailerResponse.getTrailerDatas().size() > 0) {
                mTrailerAdapter.clear();
                mTrailerAdapter.addItems(trailerResponse.getTrailerDatas());
            } else if (MovieDetailFragment.this.isResumed()) {
                mTrailerRecyclerView.setVisibility(View.GONE);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (MovieDetailFragment.this.isResumed()) {
                showError("trailers");
            }
        }
    };

    private Callback<ReviewsResponse> mReviewsCallback = new Callback<ReviewsResponse>() {
        @Override
        public void success(ReviewsResponse reviewResponse, Response response) {
            if (MovieDetailFragment.this.isResumed() && reviewResponse.getReviewDatas().size() > 0) {
                mReviewsAdapter.clear();
                mReviewsAdapter.addItems(reviewResponse.getReviewDatas());
            } else if (MovieDetailFragment.this.isResumed()) {
                mReviewsRecyclerView.setVisibility(View.GONE);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            if (MovieDetailFragment.this.isResumed()) {
                showError("reviews");
            }
        }
    };

    private void requestMovie() {
        API mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getMovie(mMovieItem.getMovieId(), mCallback);
    }

    private void requestReviews() {
        API mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getReviews(mMovieItem.getMovieId(), mReviewsCallback);
    }

    private void requestTrailers() {
        API mApi = PopularMoviesApplication.getInstance().getAPI();
        mApi.getTrailer(mMovieItem.getMovieId(), mTrailersCallback);
    }

    private static int getThemeAttribute(Resources.Theme theme, int themeAttr) {
        final TypedValue value = new TypedValue();
        theme.resolveAttribute(themeAttr, value, true);
        return value.resourceId;
    }

    private void startVideo(String itemKey) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://m.youtube.com/watch?v=" + itemKey));
        startActivity(intent);
    }

    private void showError(String type) {
        if (MovieDetailFragment.this.isVisible()) {
            Toast.makeText(getContext(), "Retrieving " + type + " failed! Check if you are online!", Toast.LENGTH_SHORT).show();
        }
    }

    private SharedPreferences getSharedPrefs() {
        return getContext().getSharedPreferences(
                Constants.PREFS_FILE, Context.MODE_PRIVATE);
    }

    private void configureTextViews() {
        String date = mMovieItem.getReleaseDate();
        mToolbar.setTitle(mMovieItem.getTitle());
        if (!TextUtils.isEmpty(date)) {
            mTitleTextView.setText(date);
        }
        mRatingTextView.setText(String.valueOf(mMovieItem.getAverageVote()));
        mSummaryTextView.setText(mMovieItem.getOverview());
    }
}
