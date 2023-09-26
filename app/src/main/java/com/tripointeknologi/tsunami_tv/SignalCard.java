package com.tripointeknologi.tsunami_tv;

import android.content.Context;
import androidx.core.content.ContextCompat;
import androidx.leanback.widget.BaseCardView;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Presenter.ViewHolder;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tripointeknologi.tsunami_tv.SignalData;

public class SignalCard extends Presenter {

    private static final int CARD_WIDTH = 200;
    private static final int CARD_HEIGHT = 50;
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        BaseCardView cardView = new BaseCardView(mContext);
        cardView.setFocusable(true);
        cardView.setFocusableInTouchMode(true);
        cardView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));

        // Atur margin untuk memberikan jarak antar kartu (misalnya, 16dp)
        int cardMargin = mContext.getResources().getDimensionPixelSize(R.dimen.card_margin);
        ViewGroup.MarginLayoutParams cardLayoutParams = new ViewGroup.MarginLayoutParams(CARD_WIDTH, CARD_HEIGHT);
        cardLayoutParams.setMargins(cardMargin, cardMargin, cardMargin, cardMargin);
        cardView.setLayoutParams(cardLayoutParams);

        // Buat layout untuk konten (status)
        TextView content = new TextView(mContext);
        content.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
        content.setFocusable(false);
        content.setFocusableInTouchMode(false);
        cardView.addView(content);

        return new ViewHolder(cardView);
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        SignalData signalData = (SignalData) item;
        BaseCardView cardView = (BaseCardView) viewHolder.view;

        // Setel konten (status) sesuai dengan objek SignalData
    }



    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Bersihkan kartu atau hentikan animasi jika ada
    }
}
