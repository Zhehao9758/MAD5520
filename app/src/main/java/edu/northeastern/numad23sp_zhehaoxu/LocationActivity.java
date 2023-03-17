package edu.northeastern.numad23sp_zhehaoxu;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class LocationActivity extends AppCompatActivity implements LocationListener {


    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private LocationManager locationManager;
    private final String latitudePre = "Latitude: ";
    private final String longitudePre = "Longitude: ";
    private final String distancePre = "Distance: ";
    private double latitude;
    private double longitude;
    private double totalDistance;
    private Location lastLocation;
    private TextView latitudeText;
    private TextView longitudeText;
    private TextView distanceText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.location);
        Button reset = findViewById(R.id.reset_distance);
        latitudeText = findViewById(R.id.latitude);
        longitudeText = findViewById(R.id.longitude);
        distanceText = findViewById(R.id.distance);

        if (savedInstanceState != null) {
            latitude = savedInstanceState.getDouble("latitude");
            longitude = savedInstanceState.getDouble("longitude");
            totalDistance = savedInstanceState.getDouble("totalDistance");
            latitudeText.setText(latitudePre + latitude);
            longitudeText.setText(longitudePre + longitude);
            distanceText.setText(distancePre + totalDistance);
        }  else{
            latitudeText.setText(latitudePre + "N/A");
            longitudeText.setText(longitudePre + "N/A");
            distanceText.setText(distancePre + "N/A");
        }


        reset.setOnClickListener(view -> {
            totalDistance = 0;
            distanceText.setText(distancePre + totalDistance);
        });

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                // TODO: show explanation dialog or toast here
                new AlertDialog.Builder(this)
                        .setTitle("Location permission needed")
                        .setMessage("This app needs the location permission to display your current location on the map.")
                        .setPositiveButton("OK", (dialog, which) -> {
                            // Request permission
                            ActivityCompat.requestPermissions(LocationActivity.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION);
                        })
                        .create()
                        .show();


        }else {
            // Permission has already been granted
            // TODO: start using location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 30, this);
        }

    }




    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble("latitude", latitude);
        outState.putDouble("longitude", longitude);
        outState.putDouble("totalDistance", totalDistance);
    }




    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();


        if (lastLocation != null) {
            Log.w("III", String.valueOf(lastLocation.distanceTo(location)));
            totalDistance += lastLocation.distanceTo(location);
        }
        lastLocation = location;

        // Update UI with total distance
        latitudeText.setText(latitudePre + latitude);
        longitudeText.setText(longitudePre + longitude);
        distanceText.setText(distancePre + totalDistance);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_LOCATION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Permission has been granted, start using location
            // TODO: start using location
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 30, this);
        } else {
            // Permission has been denied or request was cancelled
            // TODO: handle denial or cancellation
            findViewById(R.id.reminder).setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // Implement your code here for when the status changes
    }

    @Override
    public void onBackPressed(){
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("You will lose the distance, are you sure you want to exit?")
                .setPositiveButton("YES", (dialog, which) -> {
                    finish();
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }



}
