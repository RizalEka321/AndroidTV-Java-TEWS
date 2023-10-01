package com.tripointeknologi.tsunami_tv;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignalActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private LatLng location;
    private List<SignalData> signalData;
    private SignalFragment signalFragment;
    private boolean isFragmentVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));

        // Dapatkan lokasi dari Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("location")) {
            location = intent.getParcelableExtra("location");
        }

//         Inisialisasi fragment peta
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        for (SignalData signalData : signalData) {
            LatLng latLng = signalData.getLatLng();
            String locationName = signalData.getName();
            String locationStat = signalData.getStatus();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.signal_biru))
            );
        }

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));

        // 2. Pindahkan kamera ke lokasi yang diberikan melalui Intent
        if (location != null) {
            moveCamera(location);
        }
    }

    private void moveCamera(LatLng latLng) {
        // Atur kamera untuk fokus pada lokasi yang diinginkan
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng) // Lokasi yang ingin Anda fokuskan
                .zoom(12) // Tingkat zoom
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Di sini, Anda dapat menangani tombol remote yang akan digunakan
        // untuk menampilkan dan menghilangkan fragment. Contoh: tombol "UP" pada remote.

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!isFragmentVisible) {
                    // Panggil metode untuk menampilkan fragment
                    showSignalFragment();
                }
                isFragmentVisible = true; // Setel status fragment ke ditampilkan
                return true; // Mengembalikan true untuk menghentikan pengolahan lebih lanjut

            case KeyEvent.KEYCODE_BACK:
                if (isFragmentVisible) {
                    // Panggil metode untuk menghapus fragment
                    removeSignalFragment();
                    isFragmentVisible = false; // Setel status fragment ke tidak ditampilkan
                    return true; // Mengembalikan true untuk menghentikan pengolahan lebih lanjut
                }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showSignalFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        signalFragment = new SignalFragment();
        fragmentManager.beginTransaction().replace(R.id.browse_container, signalFragment).commit();
    }

    private void removeSignalFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(signalFragment).commit();
    }
}
