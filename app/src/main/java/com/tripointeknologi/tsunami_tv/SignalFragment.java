package com.tripointeknologi.tsunami_tv;

import android.os.Bundle;
import androidx.leanback.app.RowsSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ListRowPresenter;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignalFragment extends RowsSupportFragment {
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayObjectAdapter rowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        ListRow row1 = new ListRow(createCardRowAdapter());
        rowsAdapter.add(row1);
        setAdapter(rowsAdapter);
    }

    private ArrayObjectAdapter createCardRowAdapter() {
        // Inisialisasi adapter untuk baris kartu
        ArrayObjectAdapter cardRowAdapter = new ArrayObjectAdapter(new CardSignal());

        // Tambahkan kartu-kartu konten di sini
        // Contoh:
        List<SignalData> signalData = new ArrayList<>();
        signalData.add(new SignalData(new LatLng(-8.61306544945518, 114.06503372193593), "Signal 1", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.59300681908755, 114.2389213385338), "Signal 2", "Perbaikan", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));
        signalData.add(new SignalData(new LatLng(-8.44626015184728, 114.34441315926983), "Signal 3", "Aktif", "Dusun Gurit RT001 RW001 Desa Pengatigan", new Date()));

        // Tambahkan semua data SignalData ke adapter
        for (SignalData data : signalData) {
            cardRowAdapter.add(data);
        }

        return cardRowAdapter;
    }
}
