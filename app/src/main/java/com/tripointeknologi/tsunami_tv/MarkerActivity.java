package com.tripointeknologi.tsunami_tv;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.telecom.Call;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MarkerActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private List<LocationData> locationData;
    Context ctx;
    private int currentLocationIndex = 0;
    private Dialog popupD;
    private ArrayList<LocationData> source;
    private ArrayObjectAdapter rowsAdapter;
    private RowsSupportFragment rowsFragment;
    private boolean isCardVisible = false;
    private Marker cameraMarker = null;
    private Handler infoWindowHandler = new Handler();
    private static final int INFO_WINDOW_DUR = 5000;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marker);

        popupD = new Dialog(this, R.style.CustomPopupTheme);

        source = new ArrayList<>();
        source.add(new LocationData(new LatLng(-8.61306544945518, 114.06503372193593), "Marker 1", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.59300681908755, 114.2389213385338), "Marker 2", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.44626015184728, 114.34441315926983), "Marker 3", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "Marker 4", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.747144280259468, 114.44063098689058), "Signal 4", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        source.add(new LocationData(new LatLng(-8.512682654119923, 113.820399878706), "Signal 5", "Aktif,Alamat Signal 5", new Date()));
        source.add(new LocationData(new LatLng(-8.545956165457955, 113.91103708082358), "Signal 6", "Alamat Signal 6", new Date()));
        source.add(new LocationData(new LatLng(-8.478727109993299, 113.77096140482367), "Signal 7", "Alamat Signal 7", new Date()));
        source.add(new LocationData(new LatLng(-8.476010536705594, 114.1005512307058), "Signal 8", "Alamat Signal 8", new Date()));
        source.add(new LocationData(new LatLng(-8.481443664080569, 114.06484566623524), "Signal 9", "Alamat Signal 9", new Date()));
        source.add(new LocationData(new LatLng(-8.594842586240576, 114.36078987850192), "Signal 10", "Alamat Signal 10", new Date()));
        source.add(new LocationData(new LatLng(-8.52898024755826, 114.388942342796), "Signal 11", "Alamat Signal 11", new Date()));
        source.add(new LocationData(new LatLng(-8.636255315988258, 114.46172676267832), "Signal 12", "Alamat Signal 12", new Date()));
        source.add(new LocationData(new LatLng(-8.428723454520092, 114.0894495632817), "Signal 13", "Alamat Signal 13", new Date()));
        source.add(new LocationData(new LatLng(-8.263275841182562, 114.33974489526362), "Signal 14", "Alamat Signal 14", new Date()));
        source.add(new LocationData(new LatLng(-8.334943280597523, 114.21406468601312), "Signal 15", "Alamat Signal 15", new Date()));
        source.add(new LocationData(new LatLng(-8.433991331273027, 114.16081036005953), "Signal 16", "Alamat Signal 16", new Date()));
        source.add(new LocationData(new LatLng(-8.487719564540194, 114.01169824738948), "Signal 17", "Alamat Signal 17", new Date()));
        source.add(new LocationData(new LatLng(-8.25378946665479, 114.35252593349247), "Signal 18", "Alamat Signal 18", new Date()));
        source.add(new LocationData(new LatLng(-8.286463795911496, 114.27583970411929), "Signal 19", "Alamat Signal 19", new Date()));
        source.add(new LocationData(new LatLng(-8.409758504267158, 114.02660945865647), "Signal 20", "Alamat Signal 20", new Date()));
        source.add(new LocationData(new LatLng(-8.28540982759017, 114.24601728158528), "Signal 21", "Alamat Signal 21", new Date()));
        source.add(new LocationData(new LatLng(-8.501413796019042, 114.01595859346574), "Signal 22", "Alamat Signal 22", new Date()));
        source.add(new LocationData(new LatLng(-8.343373882890461, 114.19808838822703), "Signal 23", "Alamat Signal 23", new Date()));
        source.add(new LocationData(new LatLng(-8.250627291167588, 114.226845724242), "Signal 24", "Alamat Signal 24", new Date()));
        source.add(new LocationData(new LatLng(-8.404490297733256, 114.02021893954203), "Signal 25", "Alamat Signal 25", new Date()));
        source.add(new LocationData(new LatLng(-8.422896465676269, 113.99268555981146), "Signal 26", "Alamat Signal 26", new Date()));
        source.add(new LocationData(new LatLng(-8.453415222535932, 114.02230419246344), "Signal 27", "Alamat Signal 27", new Date()));
        source.add(new LocationData(new LatLng(-8.512614714018747, 114.24382688250634), "Signal 28", "Alamat Signal 28", new Date()));
        source.add(new LocationData(new LatLng(-8.46440138406977, 114.1969307141407), "Signal 29", "Alamat Signal 29", new Date()));
        source.add(new LocationData(new LatLng(-8.521768458899842, 114.21976174347661), "Signal 30", "Alamat Signal 30", new Date()));

        locationData = new ArrayList<>(source);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map_marker);
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
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.signal_biru))
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
                    cameraMarker.setTag(closestLocation);
                    cameraMarker.showInfoWindow();

                    infoWindowHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (cameraMarker != null) {
                                cameraMarker.hideInfoWindow();
                            }
                        }
                    }, INFO_WINDOW_DUR);
                }
            }
        });
    }

    private void showPopupDetailView(LocationData locationData) {
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
        rowsFragment.setAdapter(rowsAdapter);

        rowsFragment.setOnItemViewClickedListener(new OnItemViewClickedListener() {
            @Override
            public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item, RowPresenter.ViewHolder rowViewHolder, Row row) {
                if (item instanceof LocationData) {
                    LocationData location = (LocationData) item;
                    LatLng locationData = location.getLatLng();
                    moveCameraToMarkerBase(locationData);
                    showPopupDetailView(location);
                }
            }
        });
        ViewGroup.LayoutParams params = rowsFragment.getView().getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = 200;
        rowsFragment.getView().setLayoutParams(params);
    }

    private void showcard() {
        loadRows();
        rowsFragment.getView().setVisibility(View.VISIBLE);
    }

    private void hidecard() {
        rowsFragment.getView().setVisibility(View.INVISIBLE);
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
