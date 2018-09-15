package com.github.yasushi.hansel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

public class GeolocationReceiver extends BroadcastReceiver{
    private static final String TAG = GeolocationReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        Location location = intent.getParcelableExtra(GeolocationService.EXTRA_LOCATION);

        if(location !=null) {
            Log.i(TAG, "got location " + Utilities.formattedGeolocation(location));
        } else {
            Log.w(TAG, "unable to get location");
        }

    }
}
