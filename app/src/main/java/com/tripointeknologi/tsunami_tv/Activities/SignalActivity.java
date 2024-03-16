package com.tripointeknologi.tsunami_tv.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tripointeknologi.tsunami_tv.Models.M_ews;
import com.tripointeknologi.tsunami_tv.Models.M_rpu;
import com.tripointeknologi.tsunami_tv.R;
import com.tripointeknologi.tsunami_tv.Cards.SignalCardPresenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignalActivity extends AppCompatActivity implements OnMapReadyCallback {
    Dialog detail;
    int currentLocationIndex = 0;
    boolean isCardVisible = false;
    GoogleMap googleMap;
    List<M_rpu> rpu;
    ArrayObjectAdapter rowsAdapter;
    RowsSupportFragment rowsFragment;
    private DatabaseReference mDatabase;
    Marker cameraMarker = null;
    Handler infoWindowHandler = new Handler();
    int INFO_WINDOW_DUR = 5000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        detail = new Dialog(this, R.style.CustomPopupTheme);

        getData();
    }

    private void getData() {
        DatabaseReference banyuwangiRef = mDatabase.child("banyuwangi");
        DatabaseReference data = banyuwangiRef.child("rpu");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rpu = new ArrayList<>();
                for (DataSnapshot statusSnapshot : snapshot.getChildren()) {
                    M_rpu location = statusSnapshot.getValue(M_rpu.class);
                    if (location != null) {
                        rpu.add(location);
                    }
                }

                // Load the rows and map after fetching data
                loadRows();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_signal);
                assert mapFragment != null;
                mapFragment.getMapAsync(SignalActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("FirebaseError", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });
    }

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        SignalCardPresenter cardPresenter = new SignalCardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (M_rpu signal : rpu) {
            cardRowAdapter.add(signal);
        }
        ListRow row = new ListRow(cardRowAdapter);
        rowsAdapter.add(row);
        rowsFragment = (RowsSupportFragment) getSupportFragmentManager().findFragmentById(R.id.rows_fragment_signal);
        assert rowsFragment != null;
        rowsFragment.setAdapter(rowsAdapter);

        rowsFragment.setOnItemViewClickedListener((OnItemViewClickedListener) (itemViewHolder, item, rowViewHolder, row1) -> {
            if (item instanceof M_rpu) {
                M_rpu signal = (M_rpu) item;
                double latitude = Double.parseDouble(signal.getLatitude());
                double longitude = Double.parseDouble(signal.getLongitude());
                LatLng SignalData = new LatLng(latitude, longitude);
                moveCameraToMarker(SignalData);
            }
        });
        ViewGroup.LayoutParams params = rowsFragment.requireView().getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 200;
        rowsFragment.requireView().setLayoutParams(params);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        for (M_rpu rpu : rpu) {
            double latitude = Double.parseDouble(rpu.getLatitude());
            double longitude = Double.parseDouble(rpu.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);

            String locationName = rpu.getNama();
            String locationStat = rpu.getStatus();

            int markerIconResource = R.drawable.tower;
            if (locationStat.equals("Off")) {
                markerIconResource = R.drawable.signal_merah;
            }

            googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title(locationName)
                    .icon(BitmapDescriptorFactory.fromResource(markerIconResource)));
        }

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));
    }

    private void moveCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish() {
                float minDistance = Float.MAX_VALUE;
                M_rpu closestLocation = null;

                for (M_rpu signal : rpu) {
                    double latitude = Double.parseDouble(signal.getLatitude());
                    double longitude = Double.parseDouble(signal.getLongitude());
                    LatLng markerPosition = new LatLng(latitude, longitude);

                    float[] distance = new float[1];
                    Location.distanceBetween(
                            latLng.latitude, latLng.longitude, markerPosition.latitude, markerPosition.longitude, distance);

                    if (distance[0] < minDistance && distance[0] < 500) {
                        minDistance = distance[0];
                        closestLocation = signal;
                    }
                }

                if (closestLocation != null) {
                    LatLng markerPosition = new LatLng(
                            Double.parseDouble(closestLocation.getLatitude()),
                            Double.parseDouble(closestLocation.getLongitude())
                    );

                    if (cameraMarker != null) {
                        cameraMarker.remove();
                    }

                    cameraMarker = googleMap.addMarker(new MarkerOptions()
                            .position(markerPosition)
                            .title(closestLocation.getNama())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.tower))
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

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .tilt(60)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish() {
                float minDistance = Float.MAX_VALUE;
                M_rpu closestLocation = null;

                for (M_rpu signal : rpu) {
                    double latitude = Double.parseDouble(signal.getLatitude());
                    double longitude = Double.parseDouble(signal.getLongitude());
                    LatLng markerPosition = new LatLng(latitude, longitude);
                    float[] distance = new float[1];
                    Location.distanceBetween(
                            latLng.latitude, latLng.longitude, markerPosition.latitude, markerPosition.longitude, distance);
                    if (distance[0] < minDistance && distance[0] < 500) {
                        minDistance = distance[0];
                        closestLocation = signal;
                    }

                    cameraMarker = googleMap.addMarker(new MarkerOptions()
                            .position(markerPosition)
                            .title(signal.getNama())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.tower))
                    );

                    cameraMarker.setTag(closestLocation);
                }
                if (closestLocation != null) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    detail(closestLocation);
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    private void detail(M_rpu rpu) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.detail_signal, null);
        detail.setContentView(view);

        TextView nama = detail.findViewById(R.id.detail_name);
        TextView status = detail.findViewById(R.id.detail_status);
        TextView tanggal_akktifasi = detail.findViewById(R.id.detail_tanggal_aktifasi);
        TextView alamat = detail.findViewById(R.id.detail_alamat);

        nama.setText(String.format(": %s", rpu.getRpu_id()));
        status.setText(String.format(": %s", rpu.getStatus()));
        tanggal_akktifasi.setText(String.format(": %s", rpu.getTanggal()));
        alamat.setText(String.format(": %s", rpu.getAlamat()));

        Window window = detail.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.gravity = Gravity.START | Gravity.TOP;
            layoutParams.width = getResources().getDimensionPixelSize(R.dimen.custom_dialog_width);
            layoutParams.x = 10;
            layoutParams.y = 10;
            window.setAttributes(layoutParams);

            window.setWindowAnimations(R.style.SlideInAnimation);
        }
        detail.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP || keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            toggleCardVisibility();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            if (currentLocationIndex > 0) {
                currentLocationIndex--;
                M_rpu currentRpu = rpu.get(currentLocationIndex);
                double latitude = Double.parseDouble(currentRpu.getLatitude());
                double longitude = Double.parseDouble(currentRpu.getLongitude());
                LatLng targetLatLng = new LatLng(latitude, longitude);
                moveCamera(targetLatLng);
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (currentLocationIndex < rpu.size() - 1) {
                currentLocationIndex++;
                M_rpu currentRpu = rpu.get(currentLocationIndex);
                double latitude = Double.parseDouble(currentRpu.getLatitude());
                double longitude = Double.parseDouble(currentRpu.getLongitude());
                LatLng targetLatLng = new LatLng(latitude, longitude);
                moveCamera(targetLatLng);
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (currentLocationIndex >= 0 && currentLocationIndex < rpu.size()) {
                M_rpu signal = rpu.get(currentLocationIndex);
                detail(signal);
            }
        }
        return super.onKeyUp(keyCode, event);
    }

    private void showcard() {
        loadRows();
        rowsFragment.requireView().setVisibility(View.VISIBLE);
    }

    private void hidecard() {
        rowsFragment.requireView().setVisibility(View.GONE);
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
