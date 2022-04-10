package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityRegisterBinding;
import com.tolk_to_my.helpers.BaseActivity;

public class RegisterActivity extends BaseActivity {

    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.tvTool.setText(getString(R.string.register));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}