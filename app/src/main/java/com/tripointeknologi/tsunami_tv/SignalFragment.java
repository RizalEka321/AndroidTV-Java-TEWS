package com.tripointeknologi.tsunami_tv;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import androidx.leanback.app.BackgroundManager;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.RowPresenter;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignalFragment extends RowsSupportFragment {

    private static final String TAG = "SignalFragment";
    private static final int BACKGROUND_UPDATE_DELAY = 300;

    private final Handler mHandler = new Handler();
    private ArrayObjectAdapter mAdapter;
    private BackgroundManager mBackgroundManager;
    private List<SignalData> signalData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupUIElements();
        setupRows();
    }

    private void setupUIElements() {
        mBackgroundManager = BackgroundManager.getInstance(getActivity());
        mBackgroundManager.attach(getActivity().getWindow());
    }

    private void setupRows() {
        // Buat adapter dan atur ke RowsFragment
        mAdapter = new ArrayObjectAdapter(new ListRowPresenter());

        // Tambahkan data sinyal ke adapter sesuai kebutuhan Anda
        signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));

        // Buat baris list untuk menampilkan data sinyal
        ListRow row = new ListRow(createSignalListRowAdapter());

        // Tambahkan baris ke adapter
        mAdapter.add(row);

        // Atur adapter ke RowsFragment
        setAdapter(mAdapter);
    }

    private ArrayObjectAdapter createSignalListRowAdapter() {
        ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(new SignalCard());

        // Tambahkan data sinyal ke listRowAdapter sesuai kebutuhan Anda
        for (SignalData data : signalData) {
            listRowAdapter.add(data);
        }

        return listRowAdapter;
    }

    private void updateBackground(String imageUrl) {
        // Perbarui latar belakang di sini berdasarkan URL gambar atau sumber lainnya.
        // Mungkin Anda ingin menggunakan Glide atau Picasso untuk memuat gambar.
        mBackgroundManager.setDrawable(getResources().getDrawable(R.drawable.list_selector_background));
    }
}
