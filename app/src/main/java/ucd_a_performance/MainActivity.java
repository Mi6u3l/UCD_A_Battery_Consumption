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
import android.provider.CalendarContract.Calendars;
import android.database.Cursor;
import android.content.ContentResolver;
import android.net.Uri;
import android.speech.tts.TextToSpeech;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private FusedLocationProviderClient fusedLocationClient;
    private CameraManager mCameraManager;
    private Vibrator vibrator;
    private String mCameraId;
    private MediaPlayer mp;
    private boolean torchOn = false;
    private TextToSpeech textToSpeech;

    // Projection array. Creating indices for this array instead of doing
    // dynamic lookups improves performance.
    public static final String[] EVENT_PROJECTION = new String[] {
            Calendars.CALENDAR_DISPLAY_NAME,         // 0
    };

    // The indices for the projection array above.
    private static final int PROJECTION_DISPLAY_NAME_INDEX = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    textToSpeech.setLanguage(Locale.UK);
                }
            });

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

    public void getDeviceCalendar(View view) {
        final TextView feedback = findViewById(R.id.feedback);
        // Run query
        Cursor cur = null;
        ContentResolver cr = getContentResolver();
        Uri uri = Calendars.CONTENT_URI;
        // Submit the query and get a Cursor object back.
        cur = cr.query(uri, EVENT_PROJECTION, null, null, null);
        cur.moveToFirst();

        // Get the field values
        String displayName = cur.getString(PROJECTION_DISPLAY_NAME_INDEX);

        feedback.setText(displayName);
    }

    public void speak(View view) {
        textToSpeech.speak("UCD Advanced Software Engineering", TextToSpeech.QUEUE_FLUSH, null);
    }
}