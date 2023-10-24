package com.tripointeknologi.tsunami_tv;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    Context ctx;
    private GoogleMap googleMap;
    private List<LatLng> locations;
    private Button button1;
    private Button button2;
    private AnimatorSet floatUpAnimator;
    private AnimatorSet floatDownAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_main);
        ctx = this;

        // Initialize the list of locations
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));
        locations.add(new LatLng(-8.512682654119923, 113.820399878706));
        locations.add(new LatLng(-8.545956165457955, 113.91103708082358));
        locations.add(new LatLng(-8.478727109993299, 113.77096140482367));
        locations.add(new LatLng(-8.476010536705594, 114.1005512307058));
        locations.add(new LatLng(-8.481443664080569, 114.06484566623524));
        locations.add(new LatLng(-8.594842586240576, 114.36078987850192));
        locations.add(new LatLng(-8.52898024755826, 114.388942342796));
        locations.add(new LatLng(-8.636255315988258, 114.46172676267832));
        locations.add(new LatLng(-8.428723454520092, 114.0894495632817));
        locations.add(new LatLng(-8.263275841182562, 114.33974489526362));
        locations.add(new LatLng(-8.334943280597523, 114.21406468601312));
        locations.add(new LatLng(-8.433991331273027, 114.16081036005953));
        locations.add(new LatLng(-8.487719564540194, 114.01169824738948));
        locations.add(new LatLng(-8.25378946665479, 114.35252593349247));
        locations.add(new LatLng(-8.286463795911496, 114.27583970411929));
        locations.add(new LatLng(-8.409758504267158, 114.02660945865647));
        locations.add(new LatLng(-8.28540982759017, 114.24601728158528));
        locations.add(new LatLng(-8.501413796019042, 114.01595859346574));
        locations.add(new LatLng(-8.343373882890461, 114.19808838822703));
        locations.add(new LatLng(-8.250627291167588, 114.226845724242));
        locations.add(new LatLng(-8.404490297733256, 114.02021893954203));
        locations.add(new LatLng(-8.422896465676269, 113.99268555981146));
        locations.add(new LatLng(-8.453415222535932, 114.02230419246344));
        locations.add(new LatLng(-8.512614714018747, 114.24382688250634));
        locations.add(new LatLng(-8.46440138406977, 114.1969307141407));
        locations.add(new LatLng(-8.521768458899842, 114.21976174347661));

        mapFragment.getMapAsync(this);

        button1 = findViewById(R.id.button_marker);
        button2 = findViewById(R.id.button_signal);

        floatUpAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_up);
        floatDownAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_down);

        button2.requestFocus();

        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    floatUpAnimator.setTarget(v);
                    floatUpAnimator.start();
                } else {
                    floatDownAnimator.setTarget(v);
                    floatDownAnimator.start();
                }
            }
        });

        button2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    floatUpAnimator.setTarget(v);
                    floatUpAnimator.start();
                } else {
                    floatDownAnimator.setTarget(v);
                    floatDownAnimator.start();
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MarkerActivity.class);
                startActivity(intent);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignalActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));

        for (LatLng latLng : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.signal_biru)));
        }

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            selectPreviousButton();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            selectNextButton();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            performSelectedButtonAction();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void selectPreviousButton() {
        if (button1.isFocused()) {
            button2.requestFocus();
        } else if (button2.isFocused()) {
            button1.requestFocus();
        }
    }

    private void selectNextButton() {
        if (button1.isFocused()) {
            button2.requestFocus();
        } else if (button2.isFocused()) {
            button1.requestFocus();
        }
    }

    private void performSelectedButtonAction() {
        if (button1.isFocused()) {
            Intent intent = new Intent(MainActivity.this, MarkerActivity.class);
            startActivity(intent);
        } else if (button2.isFocused()) {
            Intent intent = new Intent(MainActivity.this, SignalActivity.class);
            startActivity(intent);
        }
    }
}
