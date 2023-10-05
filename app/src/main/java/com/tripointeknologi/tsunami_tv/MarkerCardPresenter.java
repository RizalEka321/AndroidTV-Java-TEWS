package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;

public class MarkerCardPresenter extends Presenter {

    private static final int CARD_WIDTH = 370;
    private static final int CARD_HEIGHT = 140;
    private Context mContext;
    private int cardMargin = 5;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_marker, parent, false);

        view.setFocusable(true);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(CARD_WIDTH, CARD_HEIGHT);
        layoutParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        LocationData location = (LocationData) item;

        TextView titleView = viewHolder.view.findViewById(R.id.title);
        TextView subtitleView = viewHolder.view.findViewById(R.id.subtitle);
        ImageView imageView = viewHolder.view.findViewById(R.id.image);

        titleView.setText(location.getName());
        subtitleView.setText(location.getDate().toString());
        imageView.setImageResource(R.drawable.signal_biru);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        viewHolder.view.requestFocus();
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
