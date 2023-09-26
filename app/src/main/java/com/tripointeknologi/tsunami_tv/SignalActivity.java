package com.tripointeknologi.tsunami_tv;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class SignalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        // Inisialisasi fragment peta
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                // Setup peta di sini jika diperlukan
            }
        });

        // Gantikan RowsFragment dengan SignalFragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        SignalFragment signalFragment = new SignalFragment();
        fragmentManager.beginTransaction().replace(R.id.browse_container, signalFragment).commit();
    }
}

