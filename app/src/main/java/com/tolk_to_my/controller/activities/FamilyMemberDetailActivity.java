package com.tolk_to_my.controller.activities;

import android.os.Bundle;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityFamilyMemberDetailBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.FamilyMember;

public class FamilyMemberDetailActivity extends BaseActivity {

    ActivityFamilyMemberDetailBinding binding;
    FamilyMember model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFamilyMemberDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        model = (FamilyMember) getIntent().getSerializableExtra(Constants.TYPE_MODEL);

        binding.appbar.tvTool.setText(getString(R.string.family_detail));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.etAge.setText(model.getAge());
        binding.etBirth.setText(model.getBirthday());
        binding.etDisability.setText(model.getDisability());
        binding.etGender.setText(model.getGender());
        binding.etId.setText(model.getId());
        binding.etName.setText(model.getName());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}