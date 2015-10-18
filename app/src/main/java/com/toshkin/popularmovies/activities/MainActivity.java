package com.toshkin.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.fragments.MoviesGridFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragments_container, MoviesGridFragment.newInstance(), MoviesGridFragment.TAG)
                    .commit();
        }
    }
}
