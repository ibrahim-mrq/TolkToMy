package com.tolk_to_my.controller.activities;

import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityPatientDetailBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;
import com.tolk_to_my.model.TreatmentHistory;

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

        binding.appbar.tvTool.setText(getString(R.string.treatment_history));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.etName.setText(patient.getPatientName());
        binding.etId.setText(patient.getPatientId());
        binding.etBirth.setText(patient.getPatientBirthday());
        binding.etDisability.setText(patient.getDisability());

        if (patient.isFirstTime()) {
            binding.tvVitalHistory.setVisibility(View.INVISIBLE);
        }

        binding.tvVitalHistory.setOnClickListener(view -> {
            startActivity(new Intent(this, TreatmentHistoryActivity.class)
                    .putExtra(Constants.TYPE_MODEL, patient)
                    .putExtra(Constants.TYPE_TITLE, Constants.TYPE_VENDOR)
            );
        });

        binding.btnAdd.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
                addTreatmentHistory();
            } else {
                Constants.showAlert(this, getString(R.string.no_internet), R.color.orange);
            }
        });
    }

    private void addTreatmentHistory() {
        if (isNotEmpty(binding.etSugar, binding.tvSugar)
                && isNotEmpty(binding.etPressure, binding.tvPressure)
                && isNotEmpty(binding.etHeart, binding.tvHeart)
                && isNotEmpty(binding.etTemperature, binding.tvTemperature)
                && isNotEmpty(binding.etHeight, binding.tvHeight)
                && isNotEmpty(binding.etWeight, binding.tvWeight)

                && isNotEmpty(binding.etName, binding.tvName)
                && isNotEmpty(binding.etId, binding.tvId)
                && isNotEmpty(binding.etBirth, binding.tvBirth)
                && isNotEmpty(binding.etDisability, binding.tvDisability)

                && isNotEmpty(binding.etNotes, binding.tvNotes)
                && isNotEmpty(binding.etTips, binding.tvTips)
                && isNotEmpty(binding.etTreatment, binding.tvTreatment)
        ) {

            TreatmentHistory treatmentHistory = new TreatmentHistory();

            treatmentHistory.setSugar(getText(binding.etSugar));
            treatmentHistory.setPressure(getText(binding.etPressure));
            treatmentHistory.setHeart(getText(binding.etHeart));
            treatmentHistory.setWeight(getText(binding.etWeight));
            treatmentHistory.setHeight(getText(binding.etHeight));
            treatmentHistory.setTemperature(getText(binding.etTemperature));

            treatmentHistory.setNotes(getText(binding.etNotes));
            treatmentHistory.setTips(getText(binding.etTips));
            treatmentHistory.setTreatment(getText(binding.etTreatment));

            treatmentHistory.setPatientName(patient.getPatientName());
            treatmentHistory.setPatientBirthday(patient.getPatientBirthday());
            treatmentHistory.setPatientId(patient.getPatientId());
            treatmentHistory.setPatientToken(patient.getPatientToken());

            treatmentHistory.setDisability(patient.getDisability());

            treatmentHistory.setDoctorGender(patient.getDoctorGender());
            treatmentHistory.setDoctorName(patient.getDoctorName());
            treatmentHistory.setDoctorToken(patient.getDoctorToken());

            treatmentHistory.setParentToken(patient.getParentToken());
            treatmentHistory.setToken("");
            treatmentHistory.setCreateData(Constants.getDate());

            enableElements(false);
            db.collection("TreatmentHistory")
                    .add(treatmentHistory)
                    .addOnSuccessListener(response -> {
                        response.update("token", response.getId());
                        db.collection("Patient").document(patient.getToken())
                                .update("firstTime", false);
                        showAlert(this, "", "تم تسجيل الجلسة العلاجية للمريض بنجاح");
                        new Handler().postDelayed(() -> {
                            enableElements(true);
                            onBackPressed();
                        }, 2000);

                    });
        }
    }

    private void enableElements(boolean enable) {
        binding.btnAdd.setEnabled(enable);
        if (!enable) {
            binding.btnAdd.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btnAdd.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_accent));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
        binding.appbar.imgBack.setEnabled(enable);
        binding.etSugar.setEnabled(enable);
        binding.etPressure.setEnabled(enable);
        binding.etHeart.setEnabled(enable);
        binding.etTemperature.setEnabled(enable);
        binding.etHeight.setEnabled(enable);
        binding.etWeight.setEnabled(enable);
        binding.etName.setEnabled(enable);
        binding.etId.setEnabled(enable);
        binding.etBirth.setEnabled(enable);
        binding.etDisability.setEnabled(enable);
        binding.etNotes.setEnabled(enable);
        binding.etTips.setEnabled(enable);
        binding.etTreatment.setEnabled(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}