package com.tolk_to_my.controller.activities;

import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivitySettingBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.utils.Utils;

public class SettingActivity extends BaseActivity {

    ActivitySettingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());
        binding.appbar.tvTool.setText(getString(R.string.setting));

        binding.imgWhatsapp.setOnClickListener(view -> {
            new Utils().openGoogleDuo(this, "535136193");
        });
        binding.imgCall.setOnClickListener(view -> {
            new Utils().openPhoneCall(this);
        });
        binding.imgMessage.setOnClickListener(view -> {
            new Utils().openPhoneMessage(this);
        });
        binding.btnLogout.setOnClickListener(view -> {
            Constants.logout(this);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}