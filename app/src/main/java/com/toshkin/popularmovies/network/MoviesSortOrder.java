package com.toshkin.popularmovies.network;

/**
 * @author Lazar
 */
public enum MoviesSortOrder {

    MOST_POPULAR("popularity.asc"),

    HIGHEST_RATED("vote_average.asc");

    private String mSorting;

    MoviesSortOrder(String mSorting) {
        this.mSorting = mSorting;
    }

    @Override
    public String toString() {
        return mSorting;
    }
}
