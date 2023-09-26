package com.tripointeknologi.tsunami_tv;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class SignalAdapter extends RecyclerView.Adapter<SignalAdapter.SignalViewHolder> {

    private List<SignalData> data;
    private OnItemClickListener listener;

    public SignalAdapter(List<SignalData> data) {
        this.data = data;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public SignalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_signal, parent, false);
        return new SignalViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SignalViewHolder holder, int position) {
        SignalData currentItem = data.get(position);

        // Mengatur tampilan item sesuai data
        holder.textViewName.setText(currentItem.getName());

        // Menangani klik item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(currentItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SignalViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;

        public SignalViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(SignalData signalData);
    }
}
