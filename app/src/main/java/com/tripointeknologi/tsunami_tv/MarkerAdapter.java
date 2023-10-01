package com.tripointeknologi.tsunami_tv;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MyView> {
    private List<LocationData> locationDataList;
    private boolean isCardVisible;
    private onItemClickListener onItemClickListener;
    private int selectedItemIndex = -1;

    public interface onItemClickListener {
        void onItemClick(LocationData locationData);
    }

    public void setSelectedItem(int index) {
        selectedItemIndex = index;
        notifyDataSetChanged();
    }

    public class MyView extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView subtitleView;

        public MyView(View view) {
            super(view);
            titleView = view.findViewById(R.id.title);
            subtitleView = view.findViewById(R.id.subtitle);
        }

    }

    public MarkerAdapter(List<LocationData> locationDataList, boolean isCardVisible, onItemClickListener onItemClickListener) {
        this.locationDataList = locationDataList;
        this.isCardVisible = isCardVisible;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public MyView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_markeritem, parent, false);
        return new MyView(itemView);
    }

    @Override
    public void onBindViewHolder(final MyView holder, final int position) {
        LocationData locationData = locationDataList.get(position);
        holder.titleView.setText(locationData.getName());
        holder.subtitleView.setText(locationData.getDate().toString());

        holder.itemView.setBackgroundColor(position == selectedItemIndex ?
                ContextCompat.getColor(holder.itemView.getContext(), R.color.selected_background) : Color.TRANSPARENT);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(locationData);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locationDataList.size();
    }

    public void setCardVisibility(boolean isVisible) {
        isCardVisible = isVisible;
        notifyDataSetChanged();
    }
}
