package com.krestone.savealife.util;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.krestone.savealife.R;
import com.mapbox.mapboxsdk.geometry.LatLng;

public class PrefsUtil {

    public static String getAuthToken(Context context) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPrefs.getString(context.getString(R.string.auth_token), "");
    }

    public static String getFirebaseToken(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.firebase_token), "");
    }

    public static LatLng getLatestLatLng(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        double latitude = prefs.getFloat(context.getString(R.string.latitude_key), 0.0f);
        double longitude = prefs.getFloat(context.getString(R.string.longitude_key), 0.0f);

        return new LatLng(latitude, longitude);
    }
}
