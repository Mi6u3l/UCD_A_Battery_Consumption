package com.example.ucd_a_performance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide(); // hide the title bar

    }

    public void startGPSTest(View view)
    {
        TextView feedback = findViewById(R.id.feedback);
        GPSTracker gps = new GPSTracker(this);
        double latitude = gps.getLatitude();
        double longitude = gps.getLongitude();
        feedback.setText("Latitude" + latitude + "Longitude" + longitude);
        gps.showSettingsAlert();
    }
}
