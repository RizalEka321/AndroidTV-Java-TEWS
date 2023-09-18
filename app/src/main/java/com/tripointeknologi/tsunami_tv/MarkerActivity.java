package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

        List<String> locationDatas = new ArrayList<>();
        for (LocationData location : locationData) {
            locationDatas.add(location.getName());
        }

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(this, R.layout.item_list, R.id.text1, locationDatas);

        ListView locationListView = findViewById(R.id.list_view);

        locationListView.setAdapter(locationAdapter);
        locationListView.setFocusable(true);
        locationListView.setFocusableInTouchMode(true);
        locationListView.requestFocus();

        locationListView.setOnItemClickListener((parent, view, position, id) -> {
            LocationData selectedLocation = locationData.get(position);
            LocationData selectedLocationName = selectedLocation.getAll();
            LatLng selectedLatLng = selectedLocation.getLatLng();
            moveCameraToMarker(selectedLatLng);
            showPopupDetailView(selectedLocationName);
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

        for (LocationData location : locationData) {
            LatLng latLng = location.getLatLng();
            String locationName = location.getLocationName();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.tanda_home))
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
        Dialog dialog = new Dialog(this, R.style.CustomPopupTheme);
        View view = LayoutInflater.from(this).inflate(R.layout.popup_detail_layout, null);
        dialog.setContentView(view);

        TextView detailTextView = dialog.findViewById(R.id.popup_detail_text);
        Window window = dialog.getWindow();
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
        locationDetails += "Location Name: " + locationData.getLocationName() + "\n";
        locationDetails += "Latitude: " + locationData.getLatLng().latitude + "\n";
        locationDetails += "Longitude: " + locationData.getLatLng().longitude + "\n";
        locationDetails += "Date: " + locationData.getDate().toString();

        detailTextView.setText(locationDetails);

        Button closeButton = view.findViewById(R.id.close_button);
        closeButton.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            selectPreviousListItem();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            selectNextListItem();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_ENTER) {
            performSelectedListItemAction();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private void selectPreviousListItem() {
        ListView locationListView = findViewById(R.id.list_view);
        int currentPosition = locationListView.getSelectedItemPosition();
        if (currentPosition > 0) {
            locationListView.setSelection(currentPosition - 1);
        }
    }


    private void selectNextListItem() {
        ListView locationListView = findViewById(R.id.list_view);
        int currentPosition = locationListView.getSelectedItemPosition();
        int itemCount = locationListView.getCount();
        if (currentPosition < itemCount - 1) {
            locationListView.setSelection(currentPosition + 1);
        }
    }

    private void performSelectedListItemAction() {
        ListView locationListView = findViewById(R.id.list_view);
        int selectedItemPosition = locationListView.getSelectedItemPosition();
        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            LocationData selectedLocation = locationData.get(selectedItemPosition);
            LatLng selectedLatlng = selectedLocation.getLatLng();
            moveCameraToMarker(selectedLatlng);

            showPopupDetailView(selectedLocation);
        }
    }
}
