package com.toshkin.popularmovies.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.fragments.MovieDetailFragment;
import com.toshkin.popularmovies.fragments.MoviesGridFragment;
import com.toshkin.popularmovies.fragments.SettingsDialogFragment;
import com.toshkin.popularmovies.interfaces.NavigationProvider;
import com.toshkin.popularmovies.network.pojo.MovieItem;

public class MainActivity extends AppCompatActivity implements NavigationProvider {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragments_container, MoviesGridFragment.newInstance(), MoviesGridFragment.TAG)
                    .addToBackStack(MoviesGridFragment.TAG)
                    .commit();
        }
    }

    @Override
    public void openMovieDetailFragment(@NonNull MovieItem item) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragments_container, MovieDetailFragment.newInstance(item), MovieDetailFragment.TAG)
                .addToBackStack(MovieDetailFragment.TAG)
                .commit();
    }

    @Override
    public void openSettingsDialog() {
        SettingsDialogFragment.newInstance().show(getSupportFragmentManager(), SettingsDialogFragment.TAG);
    }
}
