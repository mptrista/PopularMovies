package com.toshkin.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.fragments.MovieDetailFragment;
import com.toshkin.popularmovies.fragments.MoviesGridFragment;
import com.toshkin.popularmovies.fragments.SettingsDialogFragment;
import com.toshkin.popularmovies.interfaces.NavigationProvider;
import com.toshkin.popularmovies.network.pojo.MovieItem;

public class MainActivity extends AppCompatActivity implements NavigationProvider {

    private boolean mIsLargeDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        FrameLayout detailContainer = (FrameLayout) findViewById(R.id.details_container);
        mIsLargeDevice = detailContainer != null;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragments_container, MoviesGridFragment.newInstance(), MoviesGridFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void openMovieDetailFragment(@NonNull MovieItem item) {

        int containerID;
        if (!mIsLargeDevice) {
            containerID = R.id.fragments_container;
            getSupportFragmentManager().beginTransaction()
                    .replace(containerID, MovieDetailFragment.newInstance(item), MovieDetailFragment.TAG)
                    .addToBackStack(MovieDetailFragment.TAG)
                    .commit();
        } else {
            containerID = R.id.details_container;
            findViewById(R.id.details_container).setVisibility(View.VISIBLE);
            getSupportFragmentManager().beginTransaction()
                    .replace(containerID, MovieDetailFragment.newInstance(item), MovieDetailFragment.TAG)
                    .commit();
        }

    }

    @Override
    public void openSettingsDialog() {
        SettingsDialogFragment.newInstance().show(getSupportFragmentManager(), SettingsDialogFragment.TAG);
    }
}
