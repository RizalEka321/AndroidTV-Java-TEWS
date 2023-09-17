package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;

    private List<LocationData> locationData;

    TextToSpeech tts;
    Context ctx;

    private int currentLocationIndex = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        locationData = new ArrayList<>();
        locationData.add(new LocationData(new LatLng(-8.61306544945518, 114.06503372193593), "Marker 1", "JayaKarta", new Date()));
        locationData.add(new LocationData(new LatLng(-8.59300681908755, 114.2389213385338), "Marker 2", "suriname", new Date()));
        locationData.add(new LocationData(new LatLng(-8.44626015184728, 114.34441315926983), "Marker 3", "Mahaka", new Date()));
        locationData.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "Marker 4", "YourArea", new Date()));

        List<String> locationNames = new ArrayList<>();
        for (LocationData location: locationData){
            locationNames.add(location.getName());
        }
        // Buat adapter untuk ListView
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, R.layout.item_list, locationNames);

        // Set adapter ke ListView
        ListView locationListView = findViewById(R.id.list_view);
        locationListView.setAdapter(locationAdapter);

        // Set an OnItemClickListener for the ListView
        locationListView.setOnItemClickListener((parent, view, position, id) -> {
            LocationData selectedLocation = locationData.get(position);
            String selectedLocationName = selectedLocation.getName();
            LatLng selectedLatLng = selectedLocation.getLatLng();


            moveCameraToMarker(selectedLatLng);


            showPopupDetailView(selectedLocationName);
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
        ctx = this;

        Button b1 = findViewById(R.id.button_back);

        b1.setOnClickListener(v -> {
            Intent intent = new Intent(MarkerActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Menambahkan marker untuk setiap lokasi
        for (LocationData location : locationData){
            LatLng latLng = location.getLatLng();
            String locationName = location.getLocationName();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tanda_home))
            );
        }

        // Start Kamera
        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(5) // You can adjust the zoom level as needed
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void showPopupDetailView(String locationName) {
        Dialog dialog = new Dialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_detail_layout, null);
        dialog.setContentView(view);

        TextView detailTextView = dialog.findViewById(R.id.popup_detail_text);
        detailTextView.setText("Details for " + locationName);

        Button closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
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
                if (currentLocationIndex < locationData.size() - 1) {
                    currentLocationIndex++;
                }
                break;
            case LEFT:
                if (currentLocationIndex > 0) {
                    currentLocationIndex--;
                }
                break;
            case RIGHT:
                if (currentLocationIndex < locationData.size() - 1) {
                    currentLocationIndex++;
                }
                break;
        }
        updateCamera();
    }

    private void updateCamera() {
        if (currentLocationIndex < locationData.size()) {
            LatLng nextLocation = locationData.get(currentLocationIndex).getLatLng();
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(nextLocation, 12));
        }
    }

    private void closelocation(){
        googleMap.clear();
        for (LocationData location : locationData) {
            LatLng latLng = location.getLatLng();
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


