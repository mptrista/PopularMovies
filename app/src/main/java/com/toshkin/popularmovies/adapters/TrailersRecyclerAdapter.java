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
import com.toshkin.popularmovies.interfaces.ItemSelectedListener;
import com.toshkin.popularmovies.network.pojo.TrailerData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lazar
 */
public class TrailersRecyclerAdapter extends RecyclerView.Adapter<TrailersRecyclerAdapter.TrailerViewHolder> {

    public static final String TRAILER_IMAGE_URL = "http://img.youtube.com/vi/";
    public static final String TRAILER_FIRST_IMAGE = "/0.jpg";

    private List<TrailerData> mItems;
    private ItemSelectedListener mMovieListener;


    public TrailersRecyclerAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, final int position) {
        holder.onBind(mItems.get(position));
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMovieListener.onItemSelected(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void setListener(ItemSelectedListener mMovieListener) {
        this.mMovieListener = mMovieListener;
    }

    public void clear() {
        mItems.clear();
    }

    public void addItems(List<TrailerData> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<TrailerData> getItems() {
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
        mItems.addAll(adapterState.trailerDatas);
        notifyDataSetChanged();
    }

    private static class AdapterState implements Parcelable {
        List<TrailerData> trailerDatas;

        public AdapterState(List<TrailerData> movieData) {
            this.trailerDatas = movieData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeList(this.trailerDatas);
        }

        protected AdapterState(Parcel in) {
            this.trailerDatas = new ArrayList<TrailerData>();
            in.readList(this.trailerDatas, List.class.getClassLoader());
        }

        public static final Creator<AdapterState> CREATOR = new Creator<AdapterState>() {
            public AdapterState createFromParcel(Parcel source) {
                return new AdapterState(source);
            }

            public AdapterState[] newArray(int size) {
                return new AdapterState[size];
            }
        };
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private Context mContext;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.trailer_grid_item);
            mContext = itemView.getContext();
        }

        public void onBind(TrailerData item) {
            Picasso.with(mContext).cancelRequest(mImageView);
            Picasso.with(mContext)
                    .load(TRAILER_IMAGE_URL + item.getKey() + TRAILER_FIRST_IMAGE)
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .centerCrop()
                    .fit()
                    .into(mImageView);
        }
    }
}
