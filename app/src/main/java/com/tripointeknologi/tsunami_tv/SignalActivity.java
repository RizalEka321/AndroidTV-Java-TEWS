package com.tripointeknologi.tsunami_tv;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

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

    private GoogleMap googleMap;
    private LatLng location;
    private List<SignalData> signalData;
    private SignalFragment signalFragment;
    private boolean isFragmentVisible = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signal);

        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.747144280259468, 114.44063098689058), "Signal 4", "Aktif","Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.512682654119923, 113.820399878706), "Signal 5", "Aktif","Aktif,Alamat Signal 5", new Date()));
        signalData.add(new SignalData(new LatLng(-8.545956165457955, 113.91103708082358), "Signal 6", "Aktif","Alamat Signal 6", new Date()));
        signalData.add(new SignalData(new LatLng(-8.478727109993299, 113.77096140482367), "Signal 7", "Tidak Aktif","Alamat Signal 7", new Date()));
        signalData.add(new SignalData(new LatLng(-8.476010536705594, 114.1005512307058), "Signal 8", "Aktif","Alamat Signal 8", new Date()));
        signalData.add(new SignalData(new LatLng(-8.481443664080569, 114.06484566623524), "Signal 9", "Perbaikan","Alamat Signal 9", new Date()));
        signalData.add(new SignalData(new LatLng(-8.594842586240576, 114.36078987850192), "Signal 10","Aktif", "Alamat Signal 10", new Date()));
        signalData.add(new SignalData(new LatLng(-8.52898024755826, 114.388942342796), "Signal 11", "Aktif","Alamat Signal 11", new Date()));
        signalData.add(new SignalData(new LatLng(-8.636255315988258, 114.46172676267832), "Signal 12","Tidak Aktif", "Alamat Signal 12", new Date()));
        signalData.add(new SignalData(new LatLng(-8.428723454520092, 114.0894495632817), "Signal 13", "Tidak Aktif","Alamat Signal 13", new Date()));
        signalData.add(new SignalData(new LatLng(-8.263275841182562, 114.33974489526362), "Signal 14","Aktif", "Alamat Signal 14", new Date()));
        signalData.add(new SignalData(new LatLng(-8.334943280597523, 114.21406468601312), "Signal 15","Aktif", "Alamat Signal 15", new Date()));
        signalData.add(new SignalData(new LatLng(-8.433991331273027, 114.16081036005953), "Signal 16","Perbaikan", "Alamat Signal 16", new Date()));
        signalData.add(new SignalData(new LatLng(-8.487719564540194, 114.01169824738948), "Signal 17","Tidak Aktif", "Alamat Signal 17", new Date()));
        signalData.add(new SignalData(new LatLng(-8.25378946665479, 114.35252593349247), "Signal 18", "Tidak Aktif","Alamat Signal 18", new Date()));
        signalData.add(new SignalData(new LatLng(-8.286463795911496, 114.27583970411929), "Signal 19","Tidak Aktif", "Alamat Signal 19", new Date()));
        signalData.add(new SignalData(new LatLng(-8.409758504267158, 114.02660945865647), "Signal 20","Perbaikan", "Alamat Signal 20", new Date()));
        signalData.add(new SignalData(new LatLng(-8.28540982759017, 114.24601728158528), "Signal 21", "Perbaikan","Alamat Signal 21", new Date()));
        signalData.add(new SignalData(new LatLng(-8.501413796019042, 114.01595859346574), "Signal 22","Aktif", "Alamat Signal 22", new Date()));
        signalData.add(new SignalData(new LatLng(-8.343373882890461, 114.19808838822703), "Signal 23","Aktif", "Alamat Signal 23", new Date()));
        signalData.add(new SignalData(new LatLng(-8.250627291167588, 114.226845724242), "Signal 24", "Aktif","Alamat Signal 24", new Date()));
        signalData.add(new SignalData(new LatLng(-8.404490297733256, 114.02021893954203), "Signal 25","Aktif", "Alamat Signal 25", new Date()));
        signalData.add(new SignalData(new LatLng(-8.422896465676269, 113.99268555981146), "Signal 26","Aktif", "Alamat Signal 26", new Date()));
        signalData.add(new SignalData(new LatLng(-8.453415222535932, 114.02230419246344), "Signal 27","Aktif", "Alamat Signal 27", new Date()));
        signalData.add(new SignalData(new LatLng(-8.512614714018747, 114.24382688250634), "Signal 28","Aktif", "Alamat Signal 28", new Date()));
        signalData.add(new SignalData(new LatLng(-8.46440138406977, 114.1969307141407), "Signal 29", "Aktif","Alamat Signal 29", new Date()));
        signalData.add(new SignalData(new LatLng(-8.521768458899842, 114.21976174347661), "Signal 30","Aktif", "Alamat Signal 30", new Date()));


        // Dapatkan lokasi dari Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("location")) {
            location = intent.getParcelableExtra("location");
        }

//         Inisialisasi fragment peta
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_maps);
        mapFragment.getMapAsync(this);
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

        LatLng countryLatLng = new LatLng(-8.51811526213963, 114.26465950699851);
        float zoomCountry = 10;
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(countryLatLng, zoomCountry));

        // 2. Pindahkan kamera ke lokasi yang diberikan melalui Intent
        if (location != null) {
            moveCamera(location);
        }
    }

    private void moveCamera(LatLng latLng) {
        // Atur kamera untuk fokus pada lokasi yang diinginkan
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng) // Lokasi yang ingin Anda fokuskan
                .zoom(15) // Tingkat zoom
                .build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Di sini, Anda dapat menangani tombol remote yang akan digunakan
        // untuk menampilkan dan menghilangkan fragment. Contoh: tombol "UP" pada remote.

        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                if (!isFragmentVisible) {
                    // Panggil metode untuk menampilkan fragment
                    showSignalFragment();
                }
                isFragmentVisible = true; // Setel status fragment ke ditampilkan
                return true; // Mengembalikan true untuk menghentikan pengolahan lebih lanjut

            case KeyEvent.KEYCODE_BACK:
                if (isFragmentVisible) {
                    // Panggil metode untuk menghapus fragment
                    removeSignalFragment();
                    isFragmentVisible = false; // Setel status fragment ke tidak ditampilkan
                    return true; // Mengembalikan true untuk menghentikan pengolahan lebih lanjut
                }
        }

        return super.onKeyDown(keyCode, event);
    }

    private void showSignalFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        signalFragment = new SignalFragment();
        fragmentManager.beginTransaction().replace(R.id.browse_container, signalFragment).commit();
    }

    private void removeSignalFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().remove(signalFragment).commit();
    }
}
