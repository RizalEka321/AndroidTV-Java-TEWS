package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final long DELAY_MILLIS = 5000;

    Context ctx;

    TextToSpeech tts;

    private GoogleMap googleMap;
    private List<LatLng> locations;
    private int currentLocationIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_maps);
        ctx = this;

        // Initialize the list of locations
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));

        // Initialize the map
        mapFragment.getMapAsync(this);

        Button button = findViewById(R.id.button_marklist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class); // Change to MainActivity
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Add markers for each location
        for (LatLng latLng : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_aktif)));
        }

        // Move the camera to a specific location (e.g., the first location in the list)
        if (!locations.isEmpty()) {
            LatLng targetLatlng = locations.get(0); // Get the first location in the list
            float zoomLevel = 10; // Adjust the zoom level as needed
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(targetLatlng, zoomLevel));
        }
    }


    private void moveCamera(MainActivity.Direction direction) {
        switch (direction) {
            case UP:
                if (currentLocationIndex > 0) {
                    currentLocationIndex--;
                }
                break;
            case DOWN:
                if (currentLocationIndex < locations.size() - 1) {
                    currentLocationIndex++;
                }
                break;
            case LEFT:
                if (currentLocationIndex > 0) {
                    currentLocationIndex--;
                }
                break;
            case RIGHT:
                if (currentLocationIndex < locations.size() - 1) {
                    currentLocationIndex++;
                }
                break;
        }
        updateCamera();
    }

    private void updateCamera() {
        if (currentLocationIndex < locations.size()) {
            LatLng nextLocation = locations.get(currentLocationIndex);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nextLocation, 12));
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("TES", String.valueOf(keyCode));
        switch (keyCode) {
            case 19:
                // Tombol panah atas pada remote ditekan
                moveCamera(MainActivity.Direction.UP);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // Tombol panah bawah pada remote ditekan
                moveCamera(MainActivity.Direction.DOWN);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // Tombol panah kiri pada remote ditekan
                moveCamera(MainActivity.Direction.LEFT);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // Tombol panah kanan pada remote ditekan
                moveCamera(MainActivity.Direction.RIGHT);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_CENTER:
                // Tombol "OK" pada remote ditekan
                showLocationInfo();
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_BACK:
                closelocation();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void closelocation() {
        googleMap.clear();
        for (LatLng latLng : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_aktif)));
        }

        LatLng targetLatLng = new LatLng(-8.497857188384717, 114.22558996122491);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng, 10));
    }

    private void showLocationInfo() {
        if (currentLocationIndex < locations.size()) {
            LatLng currentLocation = locations.get(currentLocationIndex);
            String locationInfo = "Keterangan Lokasi: " + currentLocation.toString();
            Toast.makeText(this, locationInfo, Toast.LENGTH_SHORT).show();
            tts = new TextToSpeech(ctx, status -> {
                if(status == TextToSpeech.SUCCESS){
                    tts.setLanguage(new Locale("id", "ID"));
                    tts.speak("Ini Lokasinya!.", TextToSpeech.QUEUE_FLUSH, null);
                }
            });
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}
