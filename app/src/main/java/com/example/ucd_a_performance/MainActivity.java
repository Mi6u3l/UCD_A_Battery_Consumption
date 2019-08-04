package com.example.ucd_a_performance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.location.Location;

import java.util.Date;
import java.util.Calendar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;



public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

    }


    @SuppressLint("MissingPermission")
    public void startGPSTest(View view) {
        final TextView feedback = findViewById(R.id.feedback);
        final Date currentTime = Calendar.getInstance().getTime();

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            feedback.setText("Latitude " + location.getLatitude());// Logic to handle location object
                        }
                    }
                });
    }
}