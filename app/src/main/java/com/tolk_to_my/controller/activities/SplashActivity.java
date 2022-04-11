package com.tolk_to_my.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.helpers.Constants;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(() -> {
            if (Hawk.get(Constants.IS_LOGIN, false)) {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            } else {
                startActivity(new Intent(SplashActivity.this, ChooseLoginActivity.class));
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