package com.toshkin.popularmovies.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.toshkin.popularmovies.R;
import com.toshkin.popularmovies.utils.Constants;

/**
 * @author Lazar
 */
public class SettingsDialogFragment extends AppCompatDialogFragment {
    public static final String TAG = "SettingsDialogFragment.TAG";


    private SharedPreferences mPreferences;
    private RadioGroup mRadioGroup;

    public static SettingsDialogFragment newInstance() {
        return new SettingsDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_fragment_settings, container, false);
        mRadioGroup = (RadioGroup) rootView.findViewById(R.id.radio_group);
        setInitialCheck();
        setEventListener();
        getDialog().setTitle(getString(R.string.title_dialog_fragment));
        return rootView;
    }

    private void setInitialCheck() {
        String order = getSharedPrefs().getString(Constants.PREFS_ORDERING, Constants.ORDER_POPULAR_DESC);
        if (Constants.ORDER_POPULAR_DESC.equals(order)) {
            mRadioGroup.check(R.id.order_popularity);
        } else if (Constants.ORDER_HIGHEST_RATED.equals(order)){
            mRadioGroup.check(R.id.order_rating);
        } else {
            mRadioGroup.check(R.id.order_favorites);
        }
    }

    private void setEventListener() {
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.order_popularity) {
                    mRadioGroup.check(R.id.order_popularity);
                    getSharedPrefs().edit().putString(Constants.PREFS_ORDERING, Constants.ORDER_POPULAR_DESC).commit();
                } else if (checkedId == R.id.order_rating){
                    mRadioGroup.check(R.id.order_rating);
                    getSharedPrefs().edit().putString(Constants.PREFS_ORDERING, Constants.ORDER_HIGHEST_RATED).commit();
                } else {
                    mRadioGroup.check(R.id.order_favorites);
                    getSharedPrefs().edit().putString(Constants.PREFS_ORDERING, Constants.ORDER_FAVORITES).commit();
                }
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        SettingsDialogFragment.this.dismiss();
                    }
                };
                Handler handler = new Handler();
                handler.postDelayed(run, 500);
            }
        });
    }

    private SharedPreferences getSharedPrefs() {
        return getActivity().getSharedPreferences(
                Constants.PREFS_FILE, Context.MODE_PRIVATE);
    }
}
