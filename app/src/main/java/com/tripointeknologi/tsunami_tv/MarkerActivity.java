package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback, MarkerAdapter.onItemClickListener {
    private GoogleMap googleMap;
    private List<LocationData> locationData;
    TextToSpeech tts;
    Context ctx;
    private int currentLocationIndex = -1;
    private Dialog popupD;
    private RecyclerView recyclerView;
    private ArrayList<LocationData> source;
    private RecyclerView.LayoutManager RecyclerViewLayoutManager;
    private MarkerAdapter adapter;
    private LinearLayoutManager HorizontalLayout;
    private View ChildView;
    private int RecyclerViewItemPosition;
    private boolean isCardVisible = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recycler_list);

        source = new ArrayList<>();
        source.add(new LocationData(new LatLng(-8.61306544945518, 114.06503372193593), "Marker 1", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.59300681908755, 114.2389213385338), "Marker 2", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.44626015184728, 114.34441315926983), "Marker 3", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "Marker 4", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));

        // Initialize the locationData list with data from source
        locationData = new ArrayList<>(source);

        // Set up the RecyclerView
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(RecyclerViewLayoutManager);

        // Create an adapter and set it on the RecyclerView
        adapter = new MarkerAdapter(source, isCardVisible, this);
        HorizontalLayout = new LinearLayoutManager(MarkerActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(HorizontalLayout);
        recyclerView.setAdapter(adapter);

        popupD = new Dialog(this, R.style.CustomPopupTheme);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
        ctx = this;
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));


        for (LocationData location : locationData) {
            LatLng latLng = location.getLatLng();
            String locationName = location.getAlamat();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.signal_biru))
            );
        }

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(12) // You can adjust the zoom level as needed
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void showPopupDetailView(LocationData locationData) {
        View view = LayoutInflater.from(this).inflate(R.layout.popup_detail_layout, null);
        popupD.setContentView(view);

        TextView detailTextView = popupD.findViewById(R.id.popup_detail_text);
        Window window = popupD.getWindow();
        if (window != null) {
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            );
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.END;
            params.dimAmount = 0.0f;
            window.setAttributes(params);
        }

        // Build a string with all the location data
        String locationDetails = "Details:\n";
        locationDetails += "Name: " + locationData.getName() + "\n";
        locationDetails += "Location Name: " + locationData.getAlamat() + "\n";
        locationDetails += "Latitude: " + locationData.getLatLng().latitude + "\n";
        locationDetails += "Longitude: " + locationData.getLatLng().longitude + "\n";
        locationDetails += "Date: " + locationData.getDate().toString();

        detailTextView.setText(locationDetails);

        Button closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> {
            popupD.dismiss();
        });

        popupD.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            isCardVisible = !isCardVisible;
            adapter.setCardVisibility(isCardVisible);

            if (isCardVisible) {
                recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
            } else {
                recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
            }
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (currentLocationIndex > 0) { // Ensure index is within bounds
                currentLocationIndex--;
                adapter.setSelectedItem(currentLocationIndex);
                moveCameraToMarker(locationData.get(currentLocationIndex).getLatLng());
                Log.d("MyApp", "Moved left to index " + currentLocationIndex);
            }
            return true; // Return true to indicate that the key press was handled
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (currentLocationIndex < locationData.size() - 1) { // Ensure index is within bounds
                currentLocationIndex++;
                adapter.setSelectedItem(currentLocationIndex);
                moveCameraToMarker(locationData.get(currentLocationIndex).getLatLng());
                Log.d("MyApp", "Moved right to index " + currentLocationIndex);
            }
            return true; // Return true to indicate that the key press was handled
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            if (currentLocationIndex >= 0 && currentLocationIndex < locationData.size()) {
                adapter.setSelectedItem(currentLocationIndex);
                showPopupDetailView(locationData.get(currentLocationIndex));
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (popupD != null && popupD.isShowing()) {
            popupD.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(LocationData locationData) {
        showPopupDetailView(locationData);
    }

}
