package com.tolk_to_my.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityChooseLoginBinding;
import com.tolk_to_my.helpers.Constants;

public class ChooseLoginActivity extends AppCompatActivity {

    ActivityChooseLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        Intent intent = new Intent(this, LoginActivity.class);
        binding.appbar.tvTool.setText(getString(R.string.login));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.customer.setOnClickListener(view -> {
            intent.putExtra(Constants.USER_TYPE, Constants.TYPE_CUSTOMER);
            startActivity(intent);
        });
        binding.vendor.setOnClickListener(view -> {
            intent.putExtra(Constants.USER_TYPE, Constants.TYPE_VENDOR);
            startActivity(intent);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}