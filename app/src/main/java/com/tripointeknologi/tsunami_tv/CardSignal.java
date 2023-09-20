package com.tripointeknologi.tsunami_tv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;

public class CardSignal extends Presenter {

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_signal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        SignalData signal = (SignalData) item;
        View view = viewHolder.view;

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView nameTextView = view.findViewById(R.id.textView);

        // Isi data ke dalam tampilan kartu
        imageView.setImageResource(R.drawable.signal_biru); // Ganti dengan gambar yang sesuai
        nameTextView.setText(signal.getName());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Tidak diperlukan untuk contoh ini
    }
}
