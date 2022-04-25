package com.tolk_to_my.controller.activities;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.adapter.TreatmentHistoryAdapter;
import com.tolk_to_my.databinding.ActivityTreatmentHistoryBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;
import com.tolk_to_my.model.TreatmentHistory;

import java.util.ArrayList;

public class TreatmentHistoryActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    ActivityTreatmentHistoryBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TreatmentHistoryAdapter adapter;
    ArrayList<TreatmentHistory> list = new ArrayList<>();
    Patient patient;
    String type = "";
    String token = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTreatmentHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        patient = (Patient) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
        type = getIntent().getStringExtra(Constants.TYPE_TITLE);
        token = getIntent().getStringExtra(Constants.TYPE_ID);

        binding.appbar.tvTool.setText(getString(R.string.date_treatment_history));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.include.swipeToRefresh.setOnRefreshListener(this);
        binding.include.swipeToRefresh.setRefreshing(false);

        adapter = new TreatmentHistoryAdapter(this);
        binding.include.recyclerView.setAdapter(adapter);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.include.recyclerView.setHasFixedSize(true);
        switch (type) {
            case Constants.TYPE_VENDOR:
                loadDoctorData();
                break;
            case Constants.TYPE_CUSTOMER:
                loadCustomerData();
                break;
        }
    }

    private void loadDoctorData() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
            binding.include.statefulLayout.showLoading();
            binding.include.swipeToRefresh.setRefreshing(false);
            db.collection("TreatmentHistory")
                    .whereEqualTo("doctorToken", patient.getDoctorToken())
                    .whereEqualTo("patientToken", patient.getPatientToken())
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(TreatmentHistory.class));
                            }
                            if (list.isEmpty()) {
                                binding.include.statefulLayout.showEmpty();
                            } else {
                                binding.include.statefulLayout.showContent();
                            }
                            adapter.setList(list);
                        } else {
                            binding.include.statefulLayout.showError(
                                    getString(R.string.empty_data), view -> loadDoctorData());
                        }
                    });
        } else {
            binding.include.statefulLayout.showOffline(getString(R.string.no_internet), view -> {
                loadDoctorData();
            });
        }
    }

    private void loadCustomerData() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
            binding.include.statefulLayout.showLoading();
            binding.include.swipeToRefresh.setRefreshing(false);
            db.collection("TreatmentHistory")
                    .whereEqualTo("patientToken", token)
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(TreatmentHistory.class));
                            }
                            if (list.isEmpty()) {
                                binding.include.statefulLayout.showEmpty();
                            } else {
                                binding.include.statefulLayout.showContent();
                            }
                            adapter.setList(list);
                        } else {
                            binding.include.statefulLayout.showError(
                                    getString(R.string.empty_data), view -> loadCustomerData());
                        }
                    });
        } else {
            binding.include.statefulLayout.showOffline(getString(R.string.no_internet), view -> {
                loadCustomerData();
            });
        }
    }

    @Override
    public void onRefresh() {
        switch (type) {
            case Constants.TYPE_VENDOR:
                loadDoctorData();
                break;
            case Constants.TYPE_CUSTOMER:
                loadCustomerData();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}