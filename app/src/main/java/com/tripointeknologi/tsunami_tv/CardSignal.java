package com.tripointeknologi.tsunami_tv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;

public class CardSignal extends Presenter {

    public interface OnSignalClickListener {
        void onSignalClick(SignalData signal);
    }

    private OnSignalClickListener signalClickListener;

    public void setSignalClickListener(OnSignalClickListener listener) {
        this.signalClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_signal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        final SignalData signal = (SignalData) item;
        View view = viewHolder.view;

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView nameTextView = view.findViewById(R.id.textView);

        imageView.setImageResource(R.drawable.signal_biru); // Ganti dengan gambar yang sesuai
        nameTextView.setText(signal.getName());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signalClickListener != null) {
                    signalClickListener.onSignalClick(signal);
                }
            }
        });
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Tidak diperlukan untuk contoh ini
    }
}
