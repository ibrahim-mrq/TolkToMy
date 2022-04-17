package com.tolk_to_my.controller.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivitySplashBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends BaseActivity {

    ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);


        String path = "android.resource://" + getPackageName() + "/" + R.raw.logo_video;
        binding.video.setVideoURI(Uri.parse(path));
        binding.video.start();

        new Handler().postDelayed(() -> {
            binding.video.stopPlayback();
            if (Hawk.get(Constants.IS_LOGIN, false)) {
                if (Hawk.get(Constants.USER_TYPE).equals(Constants.TYPE_CUSTOMER)) {
                    startActivity(new Intent(this, FamilyActivity.class));
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
            } else {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
            finish();
        }, 3000);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}