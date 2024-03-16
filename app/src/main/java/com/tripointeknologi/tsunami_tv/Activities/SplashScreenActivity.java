package com.tripointeknologi.tsunami_tv.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import androidx.appcompat.app.AppCompatActivity;

import com.tripointeknologi.tsunami_tv.R;
import com.tripointeknologi.tsunami_tv.databinding.ActivitySplashBinding;

@SuppressLint("CustomSplashScreen")
public class SplashScreenActivity extends AppCompatActivity {
    ActivitySplashBinding b;
    private static final int waktu_loading = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        VideoView videoView = findViewById(R.id.splash);
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splashscreenp2);

        videoView.setVideoURI(videoUri);
        videoView.start();

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, waktu_loading);
    }
}