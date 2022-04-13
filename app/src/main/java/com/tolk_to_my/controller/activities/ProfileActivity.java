package com.tolk_to_my.controller.activities;

import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityProfileBinding;
import com.tolk_to_my.helpers.BaseActivity;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());
        binding.appbar.tvTool.setText(getString(R.string.profile));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}