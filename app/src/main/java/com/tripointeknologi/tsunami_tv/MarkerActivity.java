package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
import java.util.HashMap;
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
        locationData.add(new LocationData(new LatLng(-8.61306544945518, 114.06503372193593), "Marker 1", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        locationData.add(new LocationData(new LatLng(-8.59300681908755, 114.2389213385338), "Marker 2", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        locationData.add(new LocationData(new LatLng(-8.44626015184728, 114.34441315926983), "Marker 3", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        locationData.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "Marker 4", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        locationData.add(new LocationData(new LatLng(-8.512682654119923, 113.820399878706), "Marker 5", "Alamat Marker 5", new Date()));
        locationData.add(new LocationData(new LatLng(-8.545956165457955, 113.91103708082358), "Marker 6", "Alamat Marker 6", new Date()));
        locationData.add(new LocationData(new LatLng(-8.478727109993299, 113.77096140482367), "Marker 7", "Alamat Marker 7", new Date()));
        locationData.add(new LocationData(new LatLng(-8.476010536705594, 114.1005512307058), "Marker 8", "Alamat Marker 8", new Date()));
        locationData.add(new LocationData(new LatLng(-8.481443664080569, 114.06484566623524), "Marker 9", "Alamat Marker 9", new Date()));
        locationData.add(new LocationData(new LatLng(-8.594842586240576, 114.36078987850192), "Marker 10", "Alamat Marker 10", new Date()));
        locationData.add(new LocationData(new LatLng(-8.52898024755826, 114.388942342796), "Marker 11", "Alamat Marker 11", new Date()));
        locationData.add(new LocationData(new LatLng(-8.636255315988258, 114.46172676267832), "Marker 12", "Alamat Marker 12", new Date()));
        locationData.add(new LocationData(new LatLng(-8.428723454520092, 114.0894495632817), "Marker 13", "Alamat Marker 13", new Date()));
        locationData.add(new LocationData(new LatLng(-8.263275841182562, 114.33974489526362), "Marker 14", "Alamat Marker 14", new Date()));
        locationData.add(new LocationData(new LatLng(-8.334943280597523, 114.21406468601312), "Marker 15", "Alamat Marker 15", new Date()));
        locationData.add(new LocationData(new LatLng(-8.433991331273027, 114.16081036005953), "Marker 16", "Alamat Marker 16", new Date()));
        locationData.add(new LocationData(new LatLng(-8.487719564540194, 114.01169824738948), "Marker 17", "Alamat Marker 17", new Date()));
        locationData.add(new LocationData(new LatLng(-8.25378946665479, 114.35252593349247), "Marker 18", "Alamat Marker 18", new Date()));
        locationData.add(new LocationData(new LatLng(-8.286463795911496, 114.27583970411929), "Marker 19", "Alamat Marker 19", new Date()));
        locationData.add(new LocationData(new LatLng(-8.409758504267158, 114.02660945865647), "Marker 20", "Alamat Marker 20", new Date()));
        locationData.add(new LocationData(new LatLng(-8.28540982759017, 114.24601728158528), "Marker 21", "Alamat Marker 21", new Date()));
        locationData.add(new LocationData(new LatLng(-8.501413796019042, 114.01595859346574), "Marker 22", "Alamat Marker 22", new Date()));
        locationData.add(new LocationData(new LatLng(-8.343373882890461, 114.19808838822703), "Marker 23", "Alamat Marker 23", new Date()));
        locationData.add(new LocationData(new LatLng(-8.250627291167588, 114.226845724242), "Marker 24", "Alamat Marker 24", new Date()));
        locationData.add(new LocationData(new LatLng(-8.404490297733256, 114.02021893954203), "Marker 25", "Alamat Marker 25", new Date()));
        locationData.add(new LocationData(new LatLng(-8.422896465676269, 113.99268555981146), "Marker 26", "Alamat Marker 26", new Date()));
        locationData.add(new LocationData(new LatLng(-8.453415222535932, 114.02230419246344), "Marker 27", "Alamat Marker 27", new Date()));
        locationData.add(new LocationData(new LatLng(-8.512614714018747, 114.24382688250634), "Marker 28", "Alamat Marker 28", new Date()));
        locationData.add(new LocationData(new LatLng(-8.46440138406977, 114.1969307141407), "Marker 29", "Alamat Marker 29", new Date()));
        locationData.add(new LocationData(new LatLng(-8.521768458899842, 114.21976174347661), "Marker 30", "Alamat Marker 30", new Date()));


        List<HashMap<String, String>> locationDatas= new ArrayList<>();
        for (LocationData location : locationData) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", location.getName());
            map.put("address", location.getAlamat());
            locationDatas.add(map);
        }

        String[] from = {"name", "address"};
        int[] to = {R.id.text1_marker, R.id.text2_marker};

        // Buat adapter untuk ListView
        SimpleAdapter markerAdapter = new SimpleAdapter(this, locationDatas, R.layout.item_list_marker, from, to);


        ListView locationListView = findViewById(R.id.list_marker);

        locationListView.setAdapter(markerAdapter);
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
        locationDetails += "Location Name: " + locationData.getAlamat() + "\n";
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
        ListView locationListView = findViewById(R.id.list_marker);
        int currentPosition = locationListView.getSelectedItemPosition();
        if (currentPosition > 0) {
            locationListView.setSelection(currentPosition - 1);
        }
    }


    private void selectNextListItem() {
        ListView locationListView = findViewById(R.id.list_marker);
        int currentPosition = locationListView.getSelectedItemPosition();
        int itemCount = locationListView.getCount();
        if (currentPosition < itemCount - 1) {
            locationListView.setSelection(currentPosition + 1);
        }
    }

    private void performSelectedListItemAction() {
        ListView locationListView = findViewById(R.id.list_marker);
        int selectedItemPosition = locationListView.getSelectedItemPosition();
        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            LocationData selectedLocation = locationData.get(selectedItemPosition);
            LatLng selectedLatlng = selectedLocation.getLatLng();
            moveCameraToMarker(selectedLatlng);

            showPopupDetailView(selectedLocation);
        }
    }
}
