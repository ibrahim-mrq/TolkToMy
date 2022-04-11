package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityLoginBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(Constants.USER_TYPE);

        setTypeLogin(this,type, binding.appbar.tvTool);
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.btnLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, MainActivity.class));
        });
        binding.tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class)
                    .putExtra(Constants.USER_TYPE, type)
            );
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}