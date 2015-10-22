package com.toshkin.popularmovies.network;

/**
 * @author Lazar
 */
public enum MoviesSortOrder {

    MOST_POPULAR("popularity.desc"),

    HIGHEST_RATED("vote_average.desc");

    private String mSorting;

    MoviesSortOrder(String mSorting) {
        this.mSorting = mSorting;
    }

    @Override
    public String toString() {
        return mSorting;
    }
}
