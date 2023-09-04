package com.example.tsunami_tv;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        // Contoh penambahan marker di Indonesia
        LatLng indonesiaLatLng = new LatLng(-2.4833826, 117.8902853); // Koordinat Indonesia (misalnya di tengah Indonesia)
        googleMap.addMarker(new MarkerOptions().position(indonesiaLatLng).title("Lokasi di Indonesia"));

        // Posisi kamera akan ditargetkan ke lokasi di Indonesia
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(indonesiaLatLng, 5)); // Nilai zoom bisa disesuaikan
    }
}