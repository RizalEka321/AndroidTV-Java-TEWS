package com.tripointeknologi.tsunami_tv;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap googleMap;
    List<LocationData> locationData;
    Context ctx;
    int currentLocationIndex = 0;
    Dialog popupD;
    List<LocationData> source;
    ArrayObjectAdapter rowsAdapter;
    RowsSupportFragment rowsFragment;
    boolean isCardVisible = false;
    Marker cameraMarker = null;
    Handler infoWindowHandler = new Handler();
    int INFO_WINDOW_DUR = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        popupD = new Dialog(this, R.style.CustomPopupTheme);

        source = new ArrayList<>();
        source.add(new LocationData(new LatLng(-8.61306544945518, 114.06503372193593), "EWS 1", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.59300681908755, 114.2389213385338), "EWS 2", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.44626015184728, 114.34441315926983), "EWS 3", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "EWS 4", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.512682654119923, 113.820399878706), "EWS 5", "Aktif,Alamat EWS 5", new Date()));
        source.add(new LocationData(new LatLng(-8.545956165457955, 113.91103708082358), "EWS 6", "Alamat EWS 6", new Date()));
        source.add(new LocationData(new LatLng(-8.478727109993299, 113.77096140482367), "EWS 7", "Alamat EWS 7", new Date()));
        source.add(new LocationData(new LatLng(-8.476010536705594, 114.1005512307058), "EWS 8", "Alamat EWS 8", new Date()));
        source.add(new LocationData(new LatLng(-8.481443664080569, 114.06484566623524), "EWS 9", "Alamat EWS 9", new Date()));
        source.add(new LocationData(new LatLng(-8.594842586240576, 114.36078987850192), "EWS 10", "Alamat EWS 10", new Date()));
        source.add(new LocationData(new LatLng(-8.52898024755826, 114.388942342796), "EWS 11", "Alamat EWS 11", new Date()));
        source.add(new LocationData(new LatLng(-8.636255315988258, 114.46172676267832), "EWS 12", "Alamat EWS 12", new Date()));
        source.add(new LocationData(new LatLng(-8.428723454520092, 114.0894495632817), "EWS 13", "Alamat EWS 13", new Date()));
        source.add(new LocationData(new LatLng(-8.263275841182562, 114.33974489526362), "EWS 14", "Alamat EWS 14", new Date()));
        source.add(new LocationData(new LatLng(-8.334943280597523, 114.21406468601312), "EWS 15", "Alamat EWS 15", new Date()));
        source.add(new LocationData(new LatLng(-8.433991331273027, 114.16081036005953), "EWS 16", "Alamat EWS 16", new Date()));
        source.add(new LocationData(new LatLng(-8.487719564540194, 114.01169824738948), "EWS 17", "Alamat EWS 17", new Date()));
        source.add(new LocationData(new LatLng(-8.25378946665479, 114.35252593349247), "EWS 18", "Alamat EWS 18", new Date()));
        source.add(new LocationData(new LatLng(-8.286463795911496, 114.27583970411929), "EWS 19", "Alamat EWS 19", new Date()));
        source.add(new LocationData(new LatLng(-8.409758504267158, 114.02660945865647), "EWS 20", "Alamat EWS 20", new Date()));
        source.add(new LocationData(new LatLng(-8.28540982759017, 114.24601728158528), "EWS 21", "Alamat EWS 21", new Date()));
        source.add(new LocationData(new LatLng(-8.501413796019042, 114.01595859346574), "EWS 22", "Alamat EWS 22", new Date()));
        source.add(new LocationData(new LatLng(-8.343373882890461, 114.19808838822703), "EWS 23", "Alamat EWS 23", new Date()));
        source.add(new LocationData(new LatLng(-8.250627291167588, 114.226845724242), "EWS 24", "Alamat EWS 24", new Date()));
        source.add(new LocationData(new LatLng(-8.404490297733256, 114.02021893954203), "EWS 25", "Alamat EWS 25", new Date()));
        source.add(new LocationData(new LatLng(-8.422896465676269, 113.99268555981146), "EWS 26", "Alamat EWS 26", new Date()));
        source.add(new LocationData(new LatLng(-8.453415222535932, 114.02230419246344), "EWS 27", "Alamat EWS 27", new Date()));
        source.add(new LocationData(new LatLng(-8.512614714018747, 114.24382688250634), "EWS 28", "Alamat EWS 28", new Date()));
        source.add(new LocationData(new LatLng(-8.46440138406977, 114.1969307141407), "EWS 29", "Alamat EWS 29", new Date()));
        source.add(new LocationData(new LatLng(-8.521768458899842, 114.21976174347661), "EWS 30", "Alamat EWS 30", new Date()));

        locationData = new ArrayList<>(source);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_marker);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        ctx = this;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));

        if (cameraMarker != null) {
            cameraMarker.remove();
        }

        for (LocationData location : locationData) {
            LatLng latLng = location.getLatLng();
            String locationName = location.getAlamat();
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ews))
            );
        }

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    private void moveCameraToMarkerBase(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(12)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(12) // You can adjust the zoom level as needed
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish() {
                float minDistance = Float.MAX_VALUE;
                LocationData closestLocation = null;

                for (LocationData location : locationData) {
                    LatLng markerPosition = location.getLatLng();
                    float[] distance = new float[1];
                    Location.distanceBetween(
                            latLng.latitude, latLng.longitude, markerPosition.latitude, markerPosition.longitude, distance);
                    if (distance[0] < minDistance && distance[0] < 500) {
                        minDistance = distance[0];
                        closestLocation = location;
                    }
                }
                if (closestLocation != null) {
                    LatLng markerPosition = closestLocation.getLatLng();
                    if (cameraMarker != null) {
                        cameraMarker.remove();
                    }

                    cameraMarker = googleMap.addMarker(new MarkerOptions()
                            .position(markerPosition)
                            .title(closestLocation.getName())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.signal_biru))
                    );
                    assert cameraMarker != null;
                    cameraMarker.setTag(closestLocation);
                    cameraMarker.showInfoWindow();

                    infoWindowHandler.postDelayed(() -> {
                        if (cameraMarker != null) {
                            cameraMarker.hideInfoWindow();
                        }
                    }, INFO_WINDOW_DUR);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void showPopupDetailView(LocationData locationData) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.detail_marker, null);
        popupD.setContentView(view);

        TextView detailTextView = popupD.findViewById(R.id.popup_title);
        TextView name = popupD.findViewById(R.id.detail_name);
        TextView alamat = popupD.findViewById(R.id.detail_alamat);
        TextView latitude = popupD.findViewById(R.id.detail_latitude);
        TextView longitude = popupD.findViewById(R.id.detail_longtitude);
        TextView date = popupD.findViewById(R.id.detail_date);

        String latitudeValue = String.valueOf(locationData.getLatLng().latitude);
        String longitudeValue = String.valueOf(locationData.getLatLng().longitude);

        detailTextView.setText("Details");
        name.setText(String.format(": %s", locationData.getName()));
        alamat.setText(String.format(": %s", locationData.getAlamat()));
        latitude.setText(String.format(": %s", latitudeValue));
        longitude.setText(String.format(": %s", longitudeValue));
        date.setText(String.format(": %s", locationData.getDate()));

        Window window = popupD.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.START | Gravity.TOP;
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.custom_dialog_width);
            layoutParams.x = 10;
            layoutParams.y = 10;
            window.setAttributes(layoutParams);
            window.setWindowAnimations(R.style.SlideInAnimation);
        }

        popupD.show();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            toggleCardVisibility();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (currentLocationIndex > 0) {
                currentLocationIndex--;
                LatLng targerLatLng = locationData.get(currentLocationIndex).getLatLng();
                moveCameraToMarker(targerLatLng);
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (currentLocationIndex < locationData.size() - 1) {
                currentLocationIndex++;
                LatLng targerLatLng = locationData.get(currentLocationIndex).getLatLng();
                moveCameraToMarker(targerLatLng);
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (currentLocationIndex >= 0 && currentLocationIndex < locationData.size()) {
                LocationData location = locationData.get(currentLocationIndex);
                showPopupDetailView(location);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        if (popupD != null && popupD.isShowing()) {
            popupD.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        MarkerCardPresenter cardPresenter = new MarkerCardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (LocationData location : locationData) {
            cardRowAdapter.add(location);
        }
        ListRow row = new ListRow(cardRowAdapter);
        rowsAdapter.add(row);
        rowsFragment = (RowsSupportFragment) getSupportFragmentManager().findFragmentById(R.id.rows_fragment_marker);
        assert rowsFragment != null;
        rowsFragment.setAdapter(rowsAdapter);

        rowsFragment.setOnItemViewClickedListener((OnItemViewClickedListener) (itemViewHolder, item, rowViewHolder, row1) -> {
            if (item instanceof LocationData) {
                LocationData location = (LocationData) item;
                LatLng locationData = location.getLatLng();
                moveCameraToMarkerBase(locationData);
                showPopupDetailView(location);
            }
        });
        ViewGroup.LayoutParams params = rowsFragment.requireView().getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 200;
        rowsFragment.requireView().setLayoutParams(params);
    }

    private void showcard() {
        loadRows();
        rowsFragment.requireView().setVisibility(View.VISIBLE);
    }

    private void hidecard() {
        rowsFragment.requireView().setVisibility(View.INVISIBLE);
    }

    private void toggleCardVisibility() {
        isCardVisible = !isCardVisible;
        if (isCardVisible) {
            showcard();
        } else {
            hidecard();
        }
    }
}
