package com.toshkin.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.model.MovieItem;

import java.util.List;

/**
 * @author Lazar Toshkin
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder> {

    private List<MovieItem> mItems;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.grid_item_movie, parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.onBind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {


        public MovieViewHolder(View itemView) {
            super(itemView);
        }

        public void onBind(MovieItem item) {

        }
    }
}
