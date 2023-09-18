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
import java.util.Date;
import java.util.List;

public class SignalActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<SignalData> signalData;
    private int currentLocationIndex = -1;

    TextToSpeech tts;
    Context ctx;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Tidak Aktif", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", new Date()));
        signalData.add(new SignalData(new LatLng(-8.747144280259468, 114.44063098689058), "Signal 4", "Tidak Aktif", new Date()));

        List<String> signalDatas = new ArrayList<>();
        for (SignalData signalData : signalData){
            signalDatas.add(signalData.getNames());
        }
        // Buat adapter untuk ListView
        ArrayAdapter<String> signalAdapter = new ArrayAdapter<>(this, R.layout.item_list, R.id.text1, signalDatas);

        // Set adapter ke ListView
        ListView locationListView = findViewById(R.id.list_signal);
        locationListView.setAdapter(signalAdapter);
        locationListView.setFocusable(true);
        locationListView.setFocusableInTouchMode(true);

        locationListView.requestFocus();

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
                                SignalData selectedSignal = signalData.get(currentLocationIndex);
                                performSelectedListItemAction();
                                // Lakukan sesuatu dengan item yang dipilih
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_DPAD_DOWN:
                            // Handle navigasi ke bawah
                            if (currentLocationIndex < signalData.size() - 1) {
                                currentLocationIndex++;
                                locationListView.setSelection(currentLocationIndex);
                                SignalData selectedSignal = signalData.get(currentLocationIndex);
                                performSelectedListItemAction();
                                // Lakukan sesuatu dengan item yang dipilih
                                return true;
                            }
                            break;
                        case KeyEvent.KEYCODE_ENTER:
                            // Function ketika tombol "Enter" pada remote ditekan
                            performSelectedListItemAction();
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
        for (SignalData signalData :signalData) {
            LatLng latLng = signalData.getLatLng();
            String locationName = signalData.getNames();
            String locationStat = signalData.getKeterangan();

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

    private void performSelectedListItemAction() {
        ListView locationListView = findViewById(R.id.list_signal); // Change the ListView ID
        int selectedItemPosition = locationListView.getSelectedItemPosition();
        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            SignalData selectedSignal = signalData.get(selectedItemPosition);
            LatLng selectedLatlng = selectedSignal.getLatLng(); // Use the LatLng list
            moveCameraToMarker(selectedLatlng);

            // Show a toast message or perform any other action as needed
            String selectedLocationName = selectedSignal.getNames();
            String selectedLocationStatus = selectedSignal.getKeterangan();
            String message = "Location: " + selectedLocationName + "\nStatus: " + selectedLocationStatus;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}