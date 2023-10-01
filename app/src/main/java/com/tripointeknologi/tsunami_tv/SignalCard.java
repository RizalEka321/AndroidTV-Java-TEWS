package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Presenter.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripointeknologi.tsunami_tv.R;

public class SignalCard extends Presenter {

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 100;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_signal, null);

        BaseCardView cardView = new BaseCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.fastlane_background));

        // Set margin for the card to provide spacing between cards (e.g., 16dp)
        int cardMargin = mContext.getResources().getDimensionPixelSize(R.dimen.card_margin);
        ViewGroup.MarginLayoutParams cardLayoutParams = new ViewGroup.MarginLayoutParams(CARD_WIDTH, CARD_HEIGHT);
        cardLayoutParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        cardView.setLayoutParams(cardLayoutParams);

        cardView.addView(view);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        SignalData signalData = (SignalData) item;
        BaseCardView cardView = (BaseCardView) viewHolder.view;

        TextView contentTextView = cardView.findViewById(R.id.contentTextView);

        // Set the content (status) based on the SignalData object
        if (contentTextView != null) {
            contentTextView.setText(signalData.getName());
        }
        cardView.requestFocus();
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Clean up card or stop animations if necessary
    }
}
