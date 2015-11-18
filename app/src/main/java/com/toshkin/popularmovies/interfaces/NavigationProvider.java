package com.toshkin.popularmovies.interfaces;

import android.support.annotation.NonNull;

import com.toshkin.popularmovies.network.pojo.MovieItem;

/**
 * @author Lazar
 */
public interface NavigationProvider {
    void openMovieDetailFragment(@NonNull MovieItem item);
    void openSettingsDialog();
}
