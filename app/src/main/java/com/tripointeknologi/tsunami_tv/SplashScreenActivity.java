package com.tripointeknologi.tsunami_tv;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.tripointeknologi.tsunami_tv.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int waktu_loading = 4000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.splash);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash);

        videoView.setVideoURI(videoUri);
        videoView.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, waktu_loading);
    }
}