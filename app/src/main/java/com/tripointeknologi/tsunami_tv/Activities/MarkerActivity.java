package com.tripointeknologi.tsunami_tv.Activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tripointeknologi.tsunami_tv.Cards.MarkerCardPresenter;
import com.tripointeknologi.tsunami_tv.Models.M_ews;
import com.tripointeknologi.tsunami_tv.Models.M_rpu;
import com.tripointeknologi.tsunami_tv.R;

import java.util.ArrayList;
import java.util.List;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<M_ews> ews;
    private Context ctx;
    private Dialog popupD;
    private ArrayObjectAdapter rowsAdapter;
    private RowsSupportFragment rowsFragment;
    private Marker cameraMarker = null;
    private Handler infoWindowHandler = new Handler();
    private int INFO_WINDOW_DUR = 5000;
    private DatabaseReference mDatabase;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        popupD = new Dialog(this, R.style.CustomPopupTheme);

        getData();
    }

    private void getData() {
        DatabaseReference banyuwangiRef = mDatabase.child("banyuwangi");
        DatabaseReference data = banyuwangiRef.child("status");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ews = new ArrayList<>();
                for (DataSnapshot statusSnapshot : snapshot.getChildren()) {
                    M_ews location = statusSnapshot.getValue(M_ews.class);
                    if (location != null) {
                        ews.add(location);
                        // Log the retrieved data
                        Log.d("FirebaseData", "Device ID: " + location.getDevice_id() +
                                ", Status: " + location.getStatus() +
                                ", Tanggal: " + location.getTanggal());
                    }
                }

                // Load the rows and map after fetching data
                loadRows();
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_marker);
                assert mapFragment != null;
                mapFragment.getMapAsync(MarkerActivity.this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
                Log.e("FirebaseError", "Error fetching data from Firebase: " + databaseError.getMessage());
            }
        });
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));

        if (cameraMarker != null) {
            cameraMarker.remove();
        }

        for (M_ews location : ews) {
            double latitude = Double.parseDouble(location.getLatitude());
            double longitude = Double.parseDouble(location.getLongitude());
            LatLng latLng = new LatLng(latitude, longitude);
            String locationName = location.getStatus();
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

    private void moveCameraToMarker(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(20)
                .tilt(60)
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), new GoogleMap.CancelableCallback() {
            @Override
            public void onCancel() {

            }

            @Override
            public void onFinish() {
                float minDistance = Float.MAX_VALUE;
                M_ews closestLocation = null;

                for (M_ews location : ews) {
                    double latitude = Double.parseDouble(location.getLatitude());
                    double longitude = Double.parseDouble(location.getLongitude());
                    LatLng markerPosition = new LatLng(latitude, longitude);
                    float[] distance = new float[1];
                    Location.distanceBetween(
                            latLng.latitude, latLng.longitude, markerPosition.latitude, markerPosition.longitude, distance);
                    if (distance[0] < minDistance && distance[0] < 500) {
                        minDistance = distance[0];
                        closestLocation = location;
                    }
                }
                if (closestLocation != null) {
                    googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    detail(closestLocation);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void detail(M_ews ews) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.detail_marker, null);
        popupD.setContentView(view);

        TextView name = popupD.findViewById(R.id.detail_name);
        TextView status = popupD.findViewById(R.id.detail_status);
        TextView date = popupD.findViewById(R.id.detail_date);
        TextView lokasi = popupD.findViewById(R.id.detail_alamat);

        name.setText(String.format(": %s", ews.getDevice_id()));
        status.setText(String.format(": %s", ews.getStatus()));
        date.setText(String.format(": %s", ews.getTanggal()));
        lokasi.setText(String.format(": %s", ews.getStatus()));


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

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        MarkerCardPresenter cardPresenter = new MarkerCardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (M_ews location : ews) {
            cardRowAdapter.add(location);
        }
        ListRow row = new ListRow(cardRowAdapter);
        rowsAdapter.add(row);

        rowsFragment = (RowsSupportFragment) getSupportFragmentManager().findFragmentById(R.id.rows_fragment_marker);
        if (rowsFragment != null) {
            rowsFragment.setAdapter(rowsAdapter);

            rowsFragment.setOnItemViewClickedListener((OnItemViewClickedListener) (itemViewHolder, item, rowViewHolder, row1) -> {
                if (item instanceof M_ews) {
                    M_ews location = (M_ews) item;
                    double latitude = Double.parseDouble(location.getLatitude());
                    double longitude = Double.parseDouble(location.getLongitude());
                    LatLng ews = new LatLng(latitude, longitude);
                    moveCameraToMarker(ews);
                }
            });

            // Set layout parameters
            ViewGroup.LayoutParams params = rowsFragment.requireView().getLayoutParams();
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

            rowsFragment.requireView().setLayoutParams(params);
        }
    }
}