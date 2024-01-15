package com.tripointeknologi.tsunami_tv.Cards;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.leanback.widget.Presenter;

import com.tripointeknologi.tsunami_tv.Models.M_ews;
import com.tripointeknologi.tsunami_tv.R;

public class MarkerCardPresenter extends Presenter {

    private static final int CARD_WIDTH = 300;
    private static final int CARD_HEIGHT = 90;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        Context mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_marker, parent, false);

        view.setFocusable(true);
        ViewGroup.MarginLayoutParams layoutParams = new ViewGroup.MarginLayoutParams(CARD_WIDTH, CARD_HEIGHT);
        int cardMargin = 5;
        layoutParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        M_ews location = (M_ews) item;

        TextView titleView = viewHolder.view.findViewById(R.id.title);
        TextView subtitleView = viewHolder.view.findViewById(R.id.subtitle);
        ImageView imageView = viewHolder.view.findViewById(R.id.image);

        titleView.setText(location.getDevice_id());
        subtitleView.setText(location.getStatus());
        imageView.setImageResource(R.drawable.ews);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

//        viewHolder.view.requestFocus();
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }
}
