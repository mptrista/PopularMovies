package com.toshkin.popularmovies.network;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import retrofit.RequestInterceptor;

/**
 * @author Lazar
 */
public class ApiKeyInterceptor implements RequestInterceptor {
    public static final String TAG = "APIKeyInterceptor";

    private static final String HEADER_NAME = "api_key";

    String mApiKey;

    public ApiKeyInterceptor(Context mApplicationContext) {
        try {
            ApplicationInfo applicationInfo = mApplicationContext
                    .getPackageManager()
                    .getApplicationInfo(
                            mApplicationContext.getPackageName(),
                            PackageManager.GET_META_DATA);
            Bundle bundle = applicationInfo.metaData;
            mApiKey = bundle.getString("api-key");
        } catch (PackageManager.NameNotFoundException e) {
            Log.d(TAG, "Error retrieving API key. Add it to local.properties!");
        }
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addQueryParam(HEADER_NAME, mApiKey);
    }
}
