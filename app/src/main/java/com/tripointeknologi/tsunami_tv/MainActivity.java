package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends AppCompatActivity {

    private static final long DELAY_MILLIS = 5000;

    static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_maps);
        ctx = this;
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                googleMap.getUiSettings().setMapToolbarEnabled(true);
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                LatLng countryLatLng = new LatLng(-2.5489, 118.0149);
                float zoomLevel = 5;

                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomLevel));
            }
        });

        Button button = findViewById(R.id.button_marklist);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.ctx, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // Handle key events or any other functionality as needed
        return super.onKeyUp(keyCode, event);
    }
}
