package com.toshkin.popularmovies.adapters;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.network.pojo.ReviewData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lazar
 */
public class ReviewsRecyclerAdapter extends RecyclerView.Adapter<ReviewsRecyclerAdapter.TrailerViewHolder> {

    private List<ReviewData> mItems;

    public ReviewsRecyclerAdapter() {
        this.mItems = new ArrayList<>();
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_review, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        holder.onBind(mItems.get(position));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    public void clear() {
        mItems.clear();
    }

    public void addItems(List<ReviewData> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<ReviewData> getItems() {
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
        mItems.addAll(adapterState.reviewDatas);
        notifyDataSetChanged();
    }

    private static class AdapterState implements Parcelable {
        List<ReviewData> reviewDatas;

        public AdapterState(List<ReviewData> movieData) {
            this.reviewDatas = movieData;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(reviewDatas);
        }

        protected AdapterState(Parcel in) {
            this.reviewDatas = in.createTypedArrayList(ReviewData.CREATOR);
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
        private TextView mNameView;
        private TextView mReviewView;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            mNameView = (TextView) itemView.findViewById(R.id.reviewer_name);
            mReviewView = (TextView) itemView.findViewById(R.id.review_text);
        }

        public void onBind(ReviewData item) {
            mNameView.setText(item.getAuthorName());
            mReviewView.setClickable(true);
            mReviewView.setMovementMethod(LinkMovementMethod.getInstance());
            mReviewView.setText(Html.fromHtml(item.getContent()));
        }
    }
}