package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
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
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;
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

    private int currentLocationIndex = 0;
    private Dialog popupD;
    private GoogleMap googleMap;
    private List<SignalData> signalData;
    private ArrayObjectAdapter rowsAdapter;
    private RowsSupportFragment rowsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.747144280259468, 114.44063098689058), "Signal 4", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.512682654119923, 113.820399878706), "Signal 5", "Aktif", "Alamat Signal 5", new Date()));
        signalData.add(new SignalData(new LatLng(-8.545956165457955, 113.91103708082358), "Signal 6", "Aktif", "Alamat Signal 6", new Date()));
        signalData.add(new SignalData(new LatLng(-8.478727109993299, 113.77096140482367), "Signal 7", "Tidak Aktif", "Alamat Signal 7", new Date()));
        signalData.add(new SignalData(new LatLng(-8.476010536705594, 114.1005512307058), "Signal 8", "Aktif", "Alamat Signal 8", new Date()));
        signalData.add(new SignalData(new LatLng(-8.481443664080569, 114.06484566623524), "Signal 9", "Perbaikan", "Alamat Signal 9", new Date()));
        signalData.add(new SignalData(new LatLng(-8.594842586240576, 114.36078987850192), "Signal 10", "Aktif", "Alamat Signal 10", new Date()));
        signalData.add(new SignalData(new LatLng(-8.52898024755826, 114.388942342796), "Signal 11", "Aktif", "Alamat Signal 11", new Date()));
        signalData.add(new SignalData(new LatLng(-8.636255315988258, 114.46172676267832), "Signal 12", "Tidak Aktif", "Alamat Signal 12", new Date()));
        signalData.add(new SignalData(new LatLng(-8.428723454520092, 114.0894495632817), "Signal 13", "Tidak Aktif", "Alamat Signal 13", new Date()));
        signalData.add(new SignalData(new LatLng(-8.263275841182562, 114.33974489526362), "Signal 14", "Aktif", "Alamat Signal 14", new Date()));
        signalData.add(new SignalData(new LatLng(-8.334943280597523, 114.21406468601312), "Signal 15", "Aktif", "Alamat Signal 15", new Date()));
        signalData.add(new SignalData(new LatLng(-8.433991331273027, 114.16081036005953), "Signal 16", "Perbaikan", "Alamat Signal 16", new Date()));
        signalData.add(new SignalData(new LatLng(-8.487719564540194, 114.01169824738948), "Signal 17", "Tidak Aktif", "Alamat Signal 17", new Date()));
        signalData.add(new SignalData(new LatLng(-8.25378946665479, 114.35252593349247), "Signal 18", "Tidak Aktif", "Alamat Signal 18", new Date()));
        signalData.add(new SignalData(new LatLng(-8.286463795911496, 114.27583970411929), "Signal 19", "Tidak Aktif", "Alamat Signal 19", new Date()));
        signalData.add(new SignalData(new LatLng(-8.409758504267158, 114.02660945865647), "Signal 20", "Perbaikan", "Alamat Signal 20", new Date()));
        signalData.add(new SignalData(new LatLng(-8.28540982759017, 114.24601728158528), "Signal 21", "Perbaikan", "Alamat Signal 21", new Date()));
        signalData.add(new SignalData(new LatLng(-8.501413796019042, 114.01595859346574), "Signal 22", "Aktif", "Alamat Signal 22", new Date()));
        signalData.add(new SignalData(new LatLng(-8.343373882890461, 114.19808838822703), "Signal 23", "Aktif", "Alamat Signal 23", new Date()));
        signalData.add(new SignalData(new LatLng(-8.250627291167588, 114.226845724242), "Signal 24", "Aktif", "Alamat Signal 24", new Date()));
        signalData.add(new SignalData(new LatLng(-8.404490297733256, 114.02021893954203), "Signal 25", "Aktif", "Alamat Signal 25", new Date()));
        signalData.add(new SignalData(new LatLng(-8.422896465676269, 113.99268555981146), "Signal 26", "Aktif", "Alamat Signal 26", new Date()));
        signalData.add(new SignalData(new LatLng(-8.453415222535932, 114.02230419246344), "Signal 27", "Aktif", "Alamat Signal 27", new Date()));
        signalData.add(new SignalData(new LatLng(-8.512614714018747, 114.24382688250634), "Signal 28", "Aktif", "Alamat Signal 28", new Date()));
        signalData.add(new SignalData(new LatLng(-8.46440138406977, 114.1969307141407), "Signal 29", "Aktif", "Alamat Signal 29", new Date()));
        signalData.add(new SignalData(new LatLng(-8.521768458899842, 114.21976174347661), "Signal 30", "Aktif", "Alamat Signal 30", new Date()));


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
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
        rowsFragment = (RowsSupportFragment) getSupportFragmentManager().findFragmentById(R.id.rows_fragment);
        rowsFragment.setAdapter(rowsAdapter);

        rowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof SignalData) {
                    SignalData signal = (SignalData) item;
                    LatLng signalLocation = signal.getLatLng();
                    moveCamera(signalLocation);
                    detail(signal);
                }
            }
        });
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
            if (locationStat.equals("Perbaikan")) {
                markerIconResource = R.drawable.signal_kuning;
            } else if (locationStat.equals("Tidak Aktif")) {
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            showcard();
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            hidecard();
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


    private void showcard() {
        loadRows();
        rowsFragment.getView().setVisibility(View.VISIBLE);
    }

    private void hidecard() {
        rowsFragment.getView().setVisibility(View.GONE);
    }

    private void detail(SignalData signalData) {
        View view = LayoutInflater.from(this).inflate(R.layout.detail_signal, null);
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
        locationDetails += "Name: " + signalData.getName() + "\n";


        detailTextView.setText(locationDetails);

        popupD.show();
    }

}
