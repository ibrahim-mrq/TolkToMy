package com.tolk_to_my.controller.activities;

import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityMainBinding;
import com.tolk_to_my.helpers.BaseActivity;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.tvTool.setText(getString(R.string.app_name));
    }
}