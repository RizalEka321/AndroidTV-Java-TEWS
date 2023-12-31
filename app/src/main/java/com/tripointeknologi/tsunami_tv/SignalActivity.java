package com.tripointeknologi.tsunami_tv;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignalActivity extends AppCompatActivity implements OnMapReadyCallback {
    Context ctx;
    Dialog detail;
    int currentLocationIndex = -1;
    boolean isCardVisible = false;
    GoogleMap googleMap;
    List<SignalData> signalData;
    ArrayObjectAdapter rowsAdapter;
    RowsSupportFragment rowsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);
        ctx = this;

        detail = new Dialog(this, R.style.CustomPopupTheme);

        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.450579126087348, 114.32519941751357), "Signal 1", "Aktif", "Alamat Signal 1", "50v", "26 derajat", "04 Oktober 2023", "Sudah Bisa Digunakan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.216688925028878, 114.36196532018623), "Signal 2", "Aktif", "Alamat Signal 2", "50v", "26 derajat", "04 Oktober 2023", "Sudah Bisa Digunakan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.554158834400729, 114.10914383090599), "Signal 3", "Aktif", "Alamat Signal 3", "50v", "26 derajat", "04 Oktober 2023", "Sudah Bisa Digunakan", new Date()));

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_signal);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void loadRows() {
        rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        SignalCardPresenter cardPresenter = new SignalCardPresenter();
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(cardPresenter);
        for (SignalData signal : signalData) {
            cardRowAdapter.add(signal);
        }
        ListRow row = new ListRow(cardRowAdapter);
        rowsAdapter.add(row);
        rowsFragment = (RowsSupportFragment) getSupportFragmentManager().findFragmentById(R.id.rows_fragment_signal);
        assert rowsFragment != null;
        rowsFragment.setAdapter(rowsAdapter);

        rowsFragment.setOnItemViewClickedListener((OnItemViewClickedListener) (itemViewHolder, item, rowViewHolder, row1) -> {
            if (item instanceof SignalData) {
                SignalData signal = (SignalData) item;
                LatLng SignalData = signal.getLatLng();
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
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map));

        for (SignalData signalData : signalData) {
            LatLng latLng = signalData.getLatLng();
            String locationName = signalData.getName();
            String locationStat = signalData.getStatus();

            int markerIconResource = R.drawable.signal_biru;
            if (locationStat.equals("Tidak Aktif")) {
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
                SignalData closestLocation = null;

                for (SignalData signal : signalData) {
                    LatLng markerPosition = signal.getLatLng();
                    float[] distance = new float[1];
                    Location.distanceBetween(
                            latLng.latitude, latLng.longitude, markerPosition.latitude, markerPosition.longitude, distance);
                    if (distance[0] < minDistance && distance[0] < 500) {
                        minDistance = distance[0];
                        closestLocation = signal;
                    }
                }
                if (closestLocation != null) {
                    detail(closestLocation);
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void detail(SignalData signalData) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.detail_signal, null);
        detail.setContentView(view);

        TextView title = detail.findViewById(R.id.popup_title);
        TextView nama = detail.findViewById(R.id.popup_detail_text_name);
        TextView status = detail.findViewById(R.id.popup_detail_text_status);
        TextView voltase = detail.findViewById(R.id.popup_detail_text_voltase);
        TextView temperatur = detail.findViewById(R.id.popup_detail_text_temperatur);
        TextView tanggal_akktifasi = detail.findViewById(R.id.popup_detail_text_tanggal_aktifasi);
        TextView keterangan = detail.findViewById(R.id.popup_detail_text_keterangan);

        title.setText("Signal Details");
        nama.setText(String.format(": %s", signalData.getName()));
        status.setText(String.format(": %s", signalData.getStatus()));
        voltase.setText(String.format(": %s", signalData.getVoltase()));
        temperatur.setText(String.format(": %s", signalData.getTemperatur()));
        tanggal_akktifasi.setText(String.format(": %s", signalData.getTanggal_aktifasi()));
        keterangan.setText(String.format(": %s", signalData.getKeterangan()));

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
                LatLng targetLatLng = signalData.get(currentLocationIndex).getLatLng();
                moveCamera(targetLatLng);
            }
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            if (currentLocationIndex < signalData.size() - 1) {
                currentLocationIndex++;
                LatLng targetLatLng = signalData.get(currentLocationIndex).getLatLng();
                moveCamera(targetLatLng);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            if (currentLocationIndex >= 0 && currentLocationIndex < signalData.size()) {
                SignalData signal = signalData.get(currentLocationIndex);
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
