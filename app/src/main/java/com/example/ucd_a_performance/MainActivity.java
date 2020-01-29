package com.example.ucd_a_performance;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.location.LocationManager;

import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private CameraManager mCameraManager;
    private String mCameraId;
    private boolean torchOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {

        }


    }

    public void startGPSTest(View view) {
        GPSTracker gps = new GPSTracker(this);
        TextView feedback = findViewById(R.id.feedback);
        // check if GPS location can get Location
        if (gps.canGetLocation()) {

            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                double lon = gps.getLongitude();
                double lat = gps.getLatitude();
                feedback.setText("Latitude " + lat + " Longitude " + lon);
            }
        }
    }

    public void toggleFlashLight(View view) {
        try {
            torchOn = !torchOn;
            mCameraManager.setTorchMode(mCameraId, torchOn);
        } catch (CameraAccessException e) { }
    }
}