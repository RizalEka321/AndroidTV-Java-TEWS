package com.tripointeknologi.tsunami_tv.Activities;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tripointeknologi.tsunami_tv.Models.M_ews;
import com.tripointeknologi.tsunami_tv.Models.M_rpu;
import com.tripointeknologi.tsunami_tv.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    List<M_ews> ews;
    List<M_rpu> rpu;
    Button button1;
    Button button2;
    AnimatorSet floatUpAnimator;
    AnimatorSet floatDownAnimator;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        getDataEWS();
        getDataRPU();


        button1 = findViewById(R.id.button_marker);
        button2 = findViewById(R.id.button_signal);

        floatUpAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_up);
        floatDownAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.float_down);

        button2.requestFocus();

        button1.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                floatUpAnimator.setTarget(v);
                floatUpAnimator.start();
            } else {
                floatDownAnimator.setTarget(v);
                floatDownAnimator.start();
            }
        });

        button2.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                floatUpAnimator.setTarget(v);
                floatUpAnimator.start();
            } else {
                floatDownAnimator.setTarget(v);
                floatDownAnimator.start();
            }
        });

        button1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, MarkerActivity.class);
            startActivity(intent);
        });
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SignalActivity.class);
            startActivity(intent);
        });
    }

    private void getDataEWS() {
        DatabaseReference banyuwangiRef = mDatabase.child("banyuwangi");
        DatabaseReference data = banyuwangiRef.child("status");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ews = new ArrayList<>();
                for (DataSnapshot statusSnapshot : snapshot.getChildren()) {
                    M_ews location = statusSnapshot.getValue(M_ews.class);
                    if (location != null) {
                        ews.add(location);
                        // Log the retrieved data
                        Log.d("FirebaseData", "Device ID: " + location.getDevice_id() +
                                ", Status: " + location.getStatus() +
                                ", Tanggal: " + location.getTanggal());
                    }
                }

                // Load the rows and map after fetching data
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_main);
                assert mapFragment != null;
                mapFragment.getMapAsync(MainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("FirebaseError", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });
    }

    private void getDataRPU() {
        DatabaseReference banyuwangiRef = mDatabase.child("banyuwangi");
        DatabaseReference data = banyuwangiRef.child("rpu");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rpu = new ArrayList<>();
                for (DataSnapshot statusSnapshot : snapshot.getChildren()) {
                    M_rpu location = statusSnapshot.getValue(M_rpu.class);
                    if (location != null) {
                        rpu.add(location);
                        // Log the retrieved data
                        Log.d("FirebaseData", "Device ID: " + location.getRpu_id() +
                                ", Status: " + location.getStatus() +
                                ", Tanggal: " + location.getTanggal());
                    }
                }

                // Load the rows and map after fetching data
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_main);
                assert mapFragment != null;
                mapFragment.getMapAsync(MainActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("FirebaseError", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));

        for (M_ews location : ews) {
            double latitude = Double.parseDouble(location.getLatitude());
            double longitude = Double.parseDouble(location.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            String locationName = location.getStatus();

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ews))
            );
        }

        for (M_rpu rpu : rpu) {
            double latitude = Double.parseDouble(rpu.getLatitude());
            double longitude = Double.parseDouble(rpu.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            String locationName = rpu.getRpu_id();
            String locationStat = rpu.getStatus();

            int markerIconResource = R.drawable.signal_biru;
            if (locationStat.equals("Off")) {
                markerIconResource = R.drawable.signal_merah;
            }

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(markerIconResource)));
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
