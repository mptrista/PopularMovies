package com.toshkin.popularmovies.adapters;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.interfaces.MovieSelectedListener;
import com.toshkin.popularmovies.pojos.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lazar Toshkin
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder> {
    public static final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185/";

    private List<MovieItem> mItems;
    private MovieSelectedListener mMovieListener;


    public MoviesRecyclerAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        holder.onBind(mItems.get(position));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMovieListener.onMovieSelected(mItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setMovieListener(MovieSelectedListener mMovieListener) {
        this.mMovieListener = mMovieListener;
    }

    public void clear() {
        mItems.clear();
    }

    public void addItems(List<MovieItem> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<MovieItem> getItems() {
        return mItems;
    }

    public Parcelable onSaveInstanceState() {
        return new AdapterState(mItems);
    }

    public void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof AdapterState)) {
            throw new IllegalArgumentException("The state argument must be derived from onSaveInstanceState()");
        }

        AdapterState adapterState = (AdapterState) state;
        mItems.clear();
        mItems.addAll(adapterState.movieData);
        notifyDataSetChanged();
    }

    private static class AdapterState implements Parcelable {
        public static final Parcelable.Creator<AdapterState> CREATOR = new Parcelable.Creator<AdapterState>() {
            public AdapterState createFromParcel(Parcel source) {
                return new AdapterState(source);
            }

            public AdapterState[] newArray(int size) {
                return new AdapterState[size];
            }
        };
        List<MovieItem> movieData;

        public AdapterState(List<MovieItem> movieData) {
            this.movieData = movieData;
        }

        public AdapterState() {
        }

        protected AdapterState(Parcel in) {
            this.movieData = in.createTypedArrayList(MovieItem.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(movieData);
        }
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private Context mContext;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.movie_grid_item);
            mContext = itemView.getContext();
        }

        public void onBind(MovieItem item) {
            Picasso.with(mContext).cancelRequest(mImageView);
            Picasso.with(mContext)
                    .load(POSTER_BASE_URL + item.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .centerCrop()
                    .fit()
                    .into(mImageView);
        }
    }
}
