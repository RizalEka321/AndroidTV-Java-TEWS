package com.tripointeknologi.tsunami_tv;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
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

    private Button button;
    private Button button1;
    private AnimatorSet floatUpAnimator;
    private AnimatorSet floatDownAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        ctx = this;

        // Initialize the list of locations
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));

        // Initialize the map
        mapFragment.getMapAsync(this);

        button = findViewById(R.id.button_marker);
        button1 = findViewById(R.id.button_signal);

        // Load animators from XML resources
        floatUpAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_up);
        floatDownAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_down);

        // Set initial focus on the first button
        button.requestFocus();

        // Set focus change listeners for the buttons
        button.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Button is focused, start floating animation
                    floatUpAnimator.setTarget(v);
                    floatUpAnimator.start();
                } else {
                    // Button lost focus, start floating down animation
                    floatDownAnimator.setTarget(v);
                    floatDownAnimator.start();
                }
            }
        });

        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Button is focused, start floating animation
                    floatUpAnimator.setTarget(v);
                    floatUpAnimator.start();
                } else {
                    // Button lost focus, start floating down animation
                    floatDownAnimator.setTarget(v);
                    floatDownAnimator.start();
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarkerActivity.class); // Change to MainActivity
                startActivity(intent);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignalActivity.class); // Change to MainActivity
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
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tanda_home)));
        }

        // Start Kamera
        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    // Override method onKeyDown untuk mengendalikan pemilihan tombol dengan remote
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            // Tombol atas ditekan pada remote, pilih tombol sebelumnya
            selectPreviousButton();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            // Tombol bawah ditekan pada remote, pilih tombol berikutnya
            selectNextButton();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Tombol enter ditekan pada remote, lakukan tindakan terhadap tombol yang dipilih
            performSelectedButtonAction();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    // Metode untuk memilih tombol sebelumnya
    private void selectPreviousButton() {
        if (button.isFocused()) {
            // Jika button sedang difokuskan, pindahkan fokus ke button1
            button1.requestFocus();
        } else if (button1.isFocused()) {
            // Jika button1 sedang difokuskan, pindahkan fokus kembali ke button
            button.requestFocus();
        }
    }

    // Metode untuk memilih tombol berikutnya
    private void selectNextButton() {
        if (button.isFocused()) {
            // Jika button sedang difokuskan, pindahkan fokus ke button1
            button1.requestFocus();
        } else if (button1.isFocused()) {
            // Jika button1 sedang difokuskan, pindahkan fokus kembali ke button
            button.requestFocus();
        }
    }

    // Metode untuk melaksanakan tindakan terhadap tombol yang dipilih
    private void performSelectedButtonAction() {
        if (button.isFocused()) {
            // Tindakan yang sesuai saat button dipilih
            Intent intent = new Intent(MainActivity.this, MarkerActivity.class);
            startActivity(intent);
        } else if (button1.isFocused()) {
            // Tindakan yang sesuai saat button1 dipilih
            Intent intent = new Intent(MainActivity.this, SignalActivity.class);
            startActivity(intent);
        }
    }
}
