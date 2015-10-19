package com.toshkin.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.model.MovieItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lazar Toshkin
 */
public class MoviesRecyclerAdapter extends RecyclerView.Adapter<MoviesRecyclerAdapter.MovieViewHolder> {

    private List<MovieItem> mItems;

    public MoviesRecyclerAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_movie, parent, false);
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

    public void addItems(List<MovieItem> items) {
        mItems.addAll(items);
        int lastItem = mItems.size()-1;
        notifyItemRangeInserted(lastItem, lastItem + items.size()-1);
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private Context mContext;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mContext = itemView.getContext();
        }

        public void onBind(MovieItem item) {
            Picasso.with(mContext).cancelRequest(mImageView);
            Picasso.with(mContext)
                    .load(item.getAvatarUrl())
                    .centerCrop()
                    .into(mImageView);
        }
    }
}
