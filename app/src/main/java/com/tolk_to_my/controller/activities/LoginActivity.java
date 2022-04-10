package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityLoginBinding;
import com.tolk_to_my.helpers.BaseActivity;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.tvTool.setText(getString(R.string.login));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        binding.tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}