package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        signalData.add(new SignalData(new LatLng(-8.747144280259468, 114.44063098689058), "Signal 4", "Tidak Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan",new Date()));
        signalData.add(new SignalData(new LatLng(-8.512682654119923, 113.820399878706), "Signal 5", "Aktif", "Alamat Signal 5", new Date()));
        signalData.add(new SignalData(new LatLng(-8.545956165457955, 113.91103708082358), "Signal 6", "Perbaikan", "Alamat Signal 6", new Date()));
        signalData.add(new SignalData(new LatLng(-8.478727109993299, 113.77096140482367), "Signal 7", "Aktif", "Alamat Signal 7", new Date()));
        signalData.add(new SignalData(new LatLng(-8.476010536705594, 114.1005512307058), "Signal 8", "Tidak Aktif", "Alamat Signal 8", new Date()));
        signalData.add(new SignalData(new LatLng(-8.481443664080569, 114.06484566623524), "Signal 9", "Aktif", "Alamat Signal 9", new Date()));
        signalData.add(new SignalData(new LatLng(-8.594842586240576, 114.36078987850192), "Signal 10", "Perbaikan", "Alamat Signal 10", new Date()));
        signalData.add(new SignalData(new LatLng(-8.52898024755826, 114.388942342796), "Signal 11", "Aktif", "Alamat Signal 11", new Date()));
        signalData.add(new SignalData(new LatLng(-8.636255315988258, 114.46172676267832), "Signal 12", "Tidak Aktif", "Alamat Signal 12", new Date()));
        signalData.add(new SignalData(new LatLng(-8.428723454520092, 114.0894495632817), "Signal 13", "Aktif", "Alamat Signal 13", new Date()));
        signalData.add(new SignalData(new LatLng(-8.263275841182562, 114.33974489526362), "Signal 14", "Perbaikan", "Alamat Signal 14", new Date()));
        signalData.add(new SignalData(new LatLng(-8.334943280597523, 114.21406468601312), "Signal 15", "Aktif", "Alamat Signal 15", new Date()));
        signalData.add(new SignalData(new LatLng(-8.433991331273027, 114.16081036005953), "Signal 16", "Tidak Aktif", "Alamat Signal 16", new Date()));
        signalData.add(new SignalData(new LatLng(-8.487719564540194, 114.01169824738948), "Signal 17", "Aktif", "Alamat Signal 17", new Date()));
        signalData.add(new SignalData(new LatLng(-8.25378946665479, 114.35252593349247), "Signal 18", "Perbaikan", "Alamat Signal 18", new Date()));
        signalData.add(new SignalData(new LatLng(-8.286463795911496, 114.27583970411929), "Signal 19", "Aktif", "Alamat Signal 19", new Date()));
        signalData.add(new SignalData(new LatLng(-8.409758504267158, 114.02660945865647), "Signal 20", "Tidak Aktif", "Alamat Signal 20", new Date()));
        signalData.add(new SignalData(new LatLng(-8.28540982759017, 114.24601728158528), "Signal 21", "Aktif", "Alamat Signal 21", new Date()));
        signalData.add(new SignalData(new LatLng(-8.501413796019042, 114.01595859346574), "Signal 22", "Perbaikan", "Alamat Signal 22", new Date()));
        signalData.add(new SignalData(new LatLng(-8.343373882890461, 114.19808838822703), "Signal 23", "Aktif", "Alamat Signal 23", new Date()));
        signalData.add(new SignalData(new LatLng(-8.250627291167588, 114.226845724242), "Signal 24", "Tidak Aktif", "Alamat Signal 24", new Date()));
        signalData.add(new SignalData(new LatLng(-8.404490297733256, 114.02021893954203), "Signal 25", "Aktif", "Alamat Signal 25", new Date()));
        signalData.add(new SignalData(new LatLng(-8.422896465676269, 113.99268555981146), "Signal 26", "Perbaikan", "Alamat Signal 26", new Date()));
        signalData.add(new SignalData(new LatLng(-8.453415222535932, 114.02230419246344), "Signal 27", "Aktif", "Alamat Signal 27", new Date()));
        signalData.add(new SignalData(new LatLng(-8.512614714018747, 114.24382688250634), "Signal 28", "Tidak Aktif", "Alamat Signal 28", new Date()));
        signalData.add(new SignalData(new LatLng(-8.46440138406977, 114.1969307141407), "Signal 29", "Aktif", "Alamat Signal 29", new Date()));
        signalData.add(new SignalData(new LatLng(-8.521768458899842, 114.21976174347661), "Signal 30", "Perbaikan", "Alamat Signal 30", new Date()));

        List<HashMap<String, String>> signalListData = new ArrayList<>();
        for (SignalData signalData : signalData) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", signalData.getName());
            map.put("address", signalData.getAlamat());
            signalListData.add(map);
        }

        String[] from = {"name", "address"};
        int[] to = {R.id.text1_signal, R.id.text2_signal};

        // Buat adapter untuk ListView
        SimpleAdapter signalAdapter = new SimpleAdapter(this, signalListData, R.layout.item_list_signal, from, to);

        // Set adapter ke ListView
        ListView signalListView = findViewById(R.id.list_signal);
        signalListView.setAdapter(signalAdapter);
        signalListView.setFocusable(true);
        signalListView.setFocusableInTouchMode(true);

        signalListView.requestFocus();

        signalListView.setOnItemClickListener((parent, view, position, id) -> {
            SignalData selectedLocation = signalData.get(position);
            LatLng selectedLatLng = selectedLocation.getLatLng();
            moveCameraToMarker(selectedLatLng);
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
        ctx = this;

        // Inisialisasi TextToSpeech
        tts = new TextToSpeech(this, status -> {
            if (status == TextToSpeech.SUCCESS) {
                int result = tts.setLanguage(Locale.US);
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(ctx, "Text-to-Speech not supported in this device.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ctx, "Text-to-Speech initialization failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        // Menambahkan marker untuk setiap lokasi
        for (SignalData signalData : signalData) {
            LatLng latLng = signalData.getLatLng();
            String locationName = signalData.getName();
            String locationStat = signalData.getStatus();

            // Tentukan ikon marker berdasarkan status lokasi
            int markerIconResource = R.drawable.signal_biru;
            if (locationStat.equals("Perbaikan")) {
                markerIconResource = R.drawable.signal_kuning; // Gunakan ikon kuning jika perbaikan
            } else if (locationStat.equals("Tidak Aktif")) {
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
                .zoom(15) // Anda dapat menyesuaikan level zoom sesuai kebutuhan
                .build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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
        ListView signalListView = findViewById(R.id.list_signal);
        int currentPosition = signalListView.getSelectedItemPosition();
        if (currentPosition > 0) {
            signalListView.setSelection(currentPosition - 1);
        }
    }

    private void selectNextListItem() {
        ListView signalListView = findViewById(R.id.list_signal);
        int currentPosition = signalListView.getSelectedItemPosition();
        int itemCount = signalListView.getCount();
        if (currentPosition < itemCount - 1) {
            signalListView.setSelection(currentPosition + 1);
        }
    }

    private void performSelectedListItemAction() {
        ListView signalListView = findViewById(R.id.list_signal);
        int selectedItemPosition = signalListView.getSelectedItemPosition();
        if (selectedItemPosition != AdapterView.INVALID_POSITION) {
            SignalData selectedSignal = signalData.get(selectedItemPosition);
            LatLng selectedLatlng = selectedSignal.getLatLng();
            moveCameraToMarker(selectedLatlng);

            // Menampilkan pesan toast atau melakukan tindakan lainnya sesuai kebutuhan
            String selectedLocationName = selectedSignal.getName();
            String selectedLocationStatus = selectedSignal.getStatus();
            String message = "Location: " + selectedLocationName + "\nStatus: " + selectedLocationStatus;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }
}
