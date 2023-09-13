package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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
import java.util.Locale;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private List<LatLng> locations;
    private int currentLocationIndex = 0;

    TextToSpeech tts;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.activity_maps);
        mapFragment.getMapAsync(this);
        ctx = this;

        // Inisialisasi daftar lokasi
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap){
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Menambahkan marker untuk setiap lokasi
        for (LatLng latLng : locations){
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_aktif)));
        }
//        Start Kamera
        LatLng countryLatLng = new LatLng(-2.5489, 118.0149);
        float zoomCountry = 5;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));

        // Memulai perpindahan kamera
        LatLng targetLatlng = new LatLng(-8.497857188384717,114.22558996122491);

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatlng, 10));
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("TES",String.valueOf(keyCode));
        switch (keyCode) {
            case 19:
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
            case KeyEvent.KEYCODE_BACK:
                closelocation();
                return true;
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

    private void closelocation(){
        googleMap.clear();
        for (LatLng latLng : locations) {
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_aktif)));
        }

        LatLng targetLatLng = new LatLng(-8.497857188384717, 114.22558996122491);
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(targetLatLng,10));
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private void onClickList(){

    }
}
