package com.tripointeknologi.tsunami_tv;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.CameraUpdateFactory;

import java.util.ArrayList;
import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private List<LatLng> locations;
    private int currentLocationIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);

        // Inisialisasi daftar lokasi
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Menambahkan marker untuk setiap lokasi
        for (LatLng latLng : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_aktif)));
        }

        // Memulai perpindahan kamera
        LatLng targetLatLng = new LatLng(-8.497857188384717, 114.22558996122491);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng,10));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {



        return super.dispatchKeyEvent(event);

    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                // Tombol panah atas pada remote ditekan
                moveCamera(Direction.UP);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_DOWN:
                // Tombol panah bawah pada remote ditekan
                moveCamera(Direction.DOWN);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_LEFT:
                // Tombol panah kiri pada remote ditekan
                moveCamera(Direction.LEFT);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                // Tombol panah kanan pada remote ditekan
                moveCamera(Direction.RIGHT);
                return true; // Menghentikan event lanjutan
            case KeyEvent.KEYCODE_DPAD_CENTER:
                // Tombol "OK" pada remote ditekan
                showLocationInfo();
                return true; // Menghentikan event lanjutan
        }
        return super.onKeyDown(keyCode, event);
    }

    private void moveCamera(Direction direction) {
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
                // Tombol panah kiri pada remote ditekan
                if (currentLocationIndex > 0) {
                    currentLocationIndex--;
                }
                break;
            case RIGHT:
                // Tombol panah kanan pada remote ditekan
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

    private void showLocationInfo() {
        if (currentLocationIndex < locations.size()) {
            LatLng currentLocation = locations.get(currentLocationIndex);
            String locationInfo = "Keterangan Lokasi: " + currentLocation.toString();
            Toast.makeText(this, locationInfo, Toast.LENGTH_SHORT).show();
        }
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }
}



