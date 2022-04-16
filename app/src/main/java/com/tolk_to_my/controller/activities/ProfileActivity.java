package com.tolk_to_my.controller.activities;

import android.os.Bundle;
import android.view.View;

import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityProfileBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.User;

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
        User user = Hawk.get(Constants.USER, null);
        binding.etBirth.setText(user.getBirthday());
        binding.etEmail.setText(user.getEmail());
        binding.etGender.setText(user.getGender());
        binding.etId.setText(user.getId());
        binding.etName.setText(user.getName());
        binding.etPassword.setText(user.getPassword());
        binding.etPhone.setText(user.getPhone());
        binding.etSpecialize.setText(user.getSpecialize());
        setUserType(this, user.getType(), binding.etType);

        if (Hawk.get(Constants.USER_TYPE, "").equals(Constants.TYPE_VENDOR)) {
            binding.tvGender.setVisibility(View.VISIBLE);
            binding.tvSpecialize.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}