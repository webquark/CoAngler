package kr.co.cyberdesic.coangler.helper;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationHelper {
    private static final String LOG_TAG = "LocHelper";

    public static final int PERMISSION_REQUEST_LOCATION = 1000;

    private static FusedLocationProviderClient mFusedLocationClient = null;

    public static void getLastLocation(Activity activity, OnSuccessListener<Location> successListener) {
        if (!checkPermission(activity)) {
            requestPermission(activity);
        }

        if (mFusedLocationClient == null) {
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(activity, successListener)
                .addOnFailureListener(activity, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(LOG_TAG, "Get last location failed: " + e.getMessage());
                        e.printStackTrace();
                    }
                });
    }

    private static boolean checkPermission(Activity activity) {
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);

        if (result != PackageManager.PERMISSION_GRANTED) {
            return false;

        } else {
            return true;
        }
    }

    private static void requestPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, PERMISSION_REQUEST_LOCATION);
    }
}
