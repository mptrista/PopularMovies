package com.toshkin.popularmovies.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.pojos.MovieItem;
import com.toshkin.popularmovies.utils.Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Lazar
 */
public class MovieDetailFragment extends Fragment {
    public static final String TAG = "MovieDetailFragment.TAG";
    public static final String ARG_MOVIE_ITEM = "ARG_MOVIE_ITEM";

    private DateFormat mDateFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);

    private MovieItem mMovieItem;

    private ImageView mPosterView;
    private TextView mTitleTextView;
    private TextView mRatingTextView;
    private Button mFavoriteButton;
    private TextView mSummaryTextView;

    public static MovieDetailFragment newInstance(MovieItem item) {
        if(item == null) {
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        mPosterView = (ImageView) rootView.findViewById(R.id.poster_image_view);
        mTitleTextView = (TextView) rootView.findViewById(R.id.title_text_view);
        mRatingTextView = (TextView) rootView.findViewById(R.id.rating_text_view);
        mFavoriteButton = (Button) rootView.findViewById(R.id.favorite_button);
        mSummaryTextView = (TextView) rootView.findViewById(R.id.summary_text_view);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPosterView = null;
        mTitleTextView = null;
        mRatingTextView = null;
        mFavoriteButton = null;
        mSummaryTextView = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Picasso.with(getContext()).cancelRequest(mPosterView);
        Picasso.with(getContext())
                .load(Constants.POSTER_BASE_URL + mMovieItem.getPosterPath())
                .placeholder(R.drawable.ic_placeholder)
                .centerCrop()
                .fit()
                .into(mPosterView);
        String title = mMovieItem.getTitle();
        if (!TextUtils.isEmpty(mMovieItem.getReleaseDate())) {
            String date = mMovieItem.getReleaseDate();
            title = title + " (" + date + ")";
        }
        mTitleTextView.setText(title);
        mRatingTextView.setText(String.valueOf(mMovieItem.getAverageVote()));
        mSummaryTextView.setText(mMovieItem.getOverview());
        mFavoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Fragment should be added to favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
