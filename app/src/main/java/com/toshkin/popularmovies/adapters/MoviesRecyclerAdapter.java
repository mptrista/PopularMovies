package com.toshkin.popularmovies.adapters;

import android.content.Context;
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

    public void addItems(List<MovieItem> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
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
                    .centerCrop()
                    .fit()
                    .into(mImageView);
        }
    }
}
