package com.github.yasushi.hansel;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Utilities {

    static final String KEY_UUID = "UUID";
    static final String KEY_REQUESTING_LOCATION_UPDATES = "requesting_location_updates";

    static boolean requestingLocationUpdates(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(KEY_REQUESTING_LOCATION_UPDATES, false);
    }

    static void setRequestingLocationUpdates(Context context, boolean requestingLocationUpdates) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(KEY_REQUESTING_LOCATION_UPDATES, requestingLocationUpdates)
                .apply();
    }

    static String getUUID(Context context) {
        String uuid = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(KEY_UUID, "");

        if(uuid.equals("")) {
            uuid  = UUID.randomUUID().toString();
            PreferenceManager.getDefaultSharedPreferences(context)
                    .edit()
                    .putString(KEY_UUID, uuid)
                    .apply();
            return uuid;
        }
        return uuid;
    }


    static String formattedGeolocation(Location location) {

        return String.format("(latitude =  %s, longitude = %s) @ %s",
                location.getLatitude() + "", location.getLongitude() + "", location.getTime() + "");
    }

    static String fomattedtime(long milliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
        return formatter.format(new Date(milliseconds * 1000));
    }


    public static boolean checkPermissions (Context context) {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
    }


    public static ActivityTransitionRequest buildTransitionRequest(){
        List transactions = new ArrayList<>();

        transactions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
        );
        transactions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.ON_BICYCLE)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
        );
        transactions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
        );
        transactions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
        );

        return new ActivityTransitionRequest(transactions);
    }


}
