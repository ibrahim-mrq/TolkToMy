package com.tolk_to_my.controller.activities;

import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityPatientDetailBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;

public class PatientDetailActivity extends BaseActivity {

    ActivityPatientDetailBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        patient = (Patient) getIntent().getSerializableExtra(Constants.TYPE_MODEL);

        binding.appbar.tvTool.setText(getString(R.string.vital_signs));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.etName.setText(patient.getPatientName());
        binding.etId.setText(patient.getPatientId());
        binding.etBirth.setText(patient.getPatientBirthday());

        binding.etSugar.setText(patient.getSugar());
        binding.etPressure.setText(patient.getPressure());
        binding.etHeart.setText(patient.getHeart());
        binding.etTemperature.setText(patient.getTemperature());
        binding.etHeight.setText(patient.getHeight());
        binding.etWeight.setText(patient.getWeight());

        binding.btnSave.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {

            } else {
                Constants.showAlert(this, getString(R.string.no_internet), R.color.orange);
            }
        });
    }

    private void enableElements(boolean enable) {
        binding.btnSave.setEnabled(enable);
        if (!enable) {
            binding.btnSave.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btnSave.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_accent));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
        binding.appbar.imgBack.setEnabled(enable);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}