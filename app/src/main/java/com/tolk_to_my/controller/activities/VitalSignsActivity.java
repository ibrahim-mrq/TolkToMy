package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityVitalSignsBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;

public class VitalSignsActivity extends BaseActivity {

    ActivityVitalSignsBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    Patient patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVitalSignsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        patient = (Patient) getIntent().getSerializableExtra(Constants.TYPE_MODEL);

        binding.appbar.tvTool.setText(getString(R.string.vital_signs));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.btnSave.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
                update();
            } else {
                Constants.showAlert(this, getString(R.string.no_internet), R.color.orange);
            }
        });

    }

    private void update() {
        if (isNotEmpty(binding.etHeart, binding.tvHeart)
                && isNotEmpty(binding.etHeight, binding.tvHeight)
                && isNotEmpty(binding.etPressure, binding.tvPressure)
                && isNotEmpty(binding.etSugar, binding.tvSugar)
                && isNotEmpty(binding.etTemperature, binding.tvTemperature)
                && isNotEmpty(binding.etWeight, binding.tvWeight)
        ) {
            ArrayMap<String, Object> map = new ArrayMap<>();
            map.put("heart", getText(binding.etHeart));
            map.put("height", getText(binding.etHeight));
            map.put("pressure", getText(binding.etPressure));
            map.put("sugar", getText(binding.etSugar));
            map.put("temperature", getText(binding.etTemperature));
            map.put("weight", getText(binding.etWeight));
            map.put("firstTime", false);

            patient.setHeart(getText(binding.etHeart));
            patient.setHeight(getText(binding.etHeight));
            patient.setPressure(getText(binding.etPressure));
            patient.setSugar(getText(binding.etSugar));
            patient.setTemperature(getText(binding.etTemperature));
            patient.setWeight(getText(binding.etWeight));
            patient.setFirstTime(false);

            enableElements(false);
            db.collection("Patient")
                    .document(patient.getToken())
                    .update(map)
                    .addOnSuccessListener(unused -> {
                        showAlert(this, "", "تم تسجيل العلامات الحيوية للمريض بنجاح");
                        new Handler().postDelayed(() -> {
                            enableElements(true);
                            startActivity(new Intent(this, PatientDetailActivity.class)
                                    .putExtra(Constants.TYPE_MODEL, patient)
                            );
                            finish();
                        }, 2000);
                    });
        }
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
        binding.etSugar.setEnabled(enable);
        binding.etPressure.setEnabled(enable);
        binding.etHeart.setEnabled(enable);
        binding.etHeight.setEnabled(enable);
        binding.etTemperature.setEnabled(enable);
        binding.etWeight.setEnabled(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}