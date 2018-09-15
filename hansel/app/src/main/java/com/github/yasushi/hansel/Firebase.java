package com.github.yasushi.hansel;

import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Map;

public class Firebase {

    private static String TAG = Firebase.class.getSimpleName();
    private static FirebaseDatabase db = FirebaseDatabase.getInstance();
    private static DatabaseReference ref = db.getReference();
    private static FirebaseStorage storage = FirebaseStorage.getInstance();
    private static StorageReference storageRef = storage.getReference();

    public static void addBreadcrumb (String uuid, Location location) {
        ref.child("breadcrumbs").child(uuid).child(location.getTime() + "").setValue(new SimpleLocation(location));
    }

    public static String getNewTripId (String uuid) {
        DatabaseReference r = ref.child("trips").child(uuid).push();
        return r.getKey();
    }

    public static void addTrip (String uuid, String tripId, Map<String, Object> trip) {
        ref.child("trips").child(uuid).child(tripId).setValue(trip);
    }


    class TripDeleter implements Runnable{

        private TripDatabase database;
        private Trip trip;

        TripDeleter(TripDatabase db, Trip t) {
           database = db;
           trip = t;
        }

        @Override
        public void run() {
            database.tripDao().delete(trip.getId());
        }
    }

    // this uploads the video, and in success, adds a record to firebase
    public static UploadTask uploadClip(String name, Uri uri){
       StorageReference r = storageRef.child("clips/" + name);
       UploadTask task = r.putFile(uri);

       task.addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Log.e(TAG,"Failed to upload clip: " + e.getMessage());
           }
       });

       return task;
    }

    @IgnoreExtraProperties
    private static class SimpleLocation {

        public double latitude;
        public double longitude;
        public double altitude;

        public SimpleLocation() {
        }

        public SimpleLocation(Location location) {
            this.latitude = location.getLatitude();
            this.longitude = location.getLongitude();
            this.altitude = location.getAltitude();
        }

    }


}
