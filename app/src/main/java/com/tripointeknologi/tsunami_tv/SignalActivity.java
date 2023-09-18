package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class SignalActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<LatLng> locations;
    private List<String> locationNames;
    private List<String> locationStatus;
    private int currentLocationIndex = 0;

    TextToSpeech tts;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        // Inisialisasi daftar lokasi
        locations = new ArrayList<>();
        locations.add(new LatLng(-8.61306544945518, 114.06503372193593));
        locations.add(new LatLng(-8.59300681908755, 114.2389213385338));
        locations.add(new LatLng(-8.44626015184728, 114.34441315926983));
        locations.add(new LatLng(-8.747144280259468, 114.44063098689058));

        // Inisialisasi daftar nama lokasi
        locationNames = new ArrayList<>();
        locationNames.add("Signal 1");
        locationNames.add("Signal 2");
        locationNames.add("Signal 3");
        locationNames.add("Signal 4");

        // Inisialisasi daftar nama lokasi
        locationStatus = new ArrayList<>();
        locationStatus.add("Aktif");
        locationStatus.add("Tidak Aktif");
        locationStatus.add("Aktif");
        locationStatus.add("Tidak Aktif");

        // Buat adapter untuk ListView
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, R.layout.item_list, R.id.text1, locationNames);

        // Set adapter ke ListView
        ListView locationListView = findViewById(R.id.list_signal);
        locationListView.setAdapter(locationAdapter);

        // Set an OnItemClickListener for the ListView
        locationListView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_UP:
                            // Handle navigasi ke atas
                            if (currentLocationIndex > 0) {
                                currentLocationIndex--;
                                locationListView.setSelection(currentLocationIndex);
                                String selectedLocation = locationNames.get(currentLocationIndex);
                                // Lakukan sesuatu dengan item yang dipilih
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            // Handle navigasi ke bawah
                            if (currentLocationIndex < locationNames.size() - 1) {
                                currentLocationIndex++;
                                locationListView.setSelection(currentLocationIndex);
                                String selectedLocation = locationNames.get(currentLocationIndex);
                                // Lakukan sesuatu dengan item yang dipilih
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                            // Handle ketika tombol "Enter" pada remote ditekan
                            String selectedLocation = locationNames.get(currentLocationIndex);
                            Toast.makeText(SignalActivity.this, "Selected location: " + selectedLocation, Toast.LENGTH_SHORT).show();
                            // Lakukan sesuatu dengan item yang dipilih
                            return true;
                    }
                }
                return false;
            }
        });


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
        ctx = this;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Menambahkan marker untuk setiap lokasi
        for (int i = 0; i < locations.size(); i++) {
            LatLng latLng = locations.get(i);
            String locationName = locationNames.get(i);
            String locationStat = locationStatus.get(i);

            // Tentukan ikon marker berdasarkan status lokasi
            int markerIconResource = R.drawable.signal_kuning;
            if (locationStat.equals("Tidak Aktif")) {
                markerIconResource = R.drawable.signal_merah; // Gunakan ikon merah jika tidak aktif
            }

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(markerIconResource)));
        }

        // Start Kamera
        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15) // You can adjust the zoom level as needed
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
