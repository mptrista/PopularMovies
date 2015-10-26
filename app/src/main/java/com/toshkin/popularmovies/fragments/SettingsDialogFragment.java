package com.toshkin.popularmovies.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.toshkin.popularmovies.R;

/**
 * @author Lazar
 */
public class SettingsDialogFragment extends DialogFragment {
    public static final String TAG = "SettingsDialogFragment.TAG";

    private SharedPreferences mPreferences;

    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_settings, container, false);
        return rootView;
    }

    private SharedPreferences getSharedPrefs() {
        if (mPreferences != null) {
            return mPreferences;
        } else {
            Context context = getActivity();
            mPreferences = context.getSharedPreferences(
                    getString(R.string.preference_file), Context.MODE_PRIVATE);
            return mPreferences;
        }
    }
}
