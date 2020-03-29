package ucd_a_performance;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Vibrator;

import android.view.View;
import android.location.Location;
import android.media.MediaPlayer;


import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraAccessException;
import android.widget.TextView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private CameraManager mCameraManager;
    private Vibrator vibrator;
    private String mCameraId;
    private MediaPlayer mp;
    private boolean torchOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {}
    }


    public void startGPSTest(View view) {
        final TextView feedback = findViewById(R.id.feedback);
        fusedLocationClient.getLastLocation()
            .addOnSuccessListener(this, new OnSuccessListener <Location> () {
                @Override
                public void onSuccess(Location location) {
                    // Got last known loation. In some rare situations this can be null.
                    if (location != null) {
                        double lon = location.getLongitude();
                        double lat = location.getLatitude();
                        // Renders location on the main view
                        feedback.setText("Latitude " + lat + " Longitude " + lon);
                    }
                }
            });

    }

    public void toggleFlashLight(View view) {
        torchOn = !torchOn;
        try {
            mCameraManager.setTorchMode(mCameraId, torchOn);
        }  catch (CameraAccessException e) {}
    }

    public void vibrate(View view) {
        vibrator.vibrate(1000);
    }

    public void playSound(View view) {
        mp = MediaPlayer.create(this, R.raw.sound);
        mp.start();
    }
}