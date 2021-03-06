package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityFamilyDetailBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.FamilyMember;
import com.tolk_to_my.model.Request;
import com.tolk_to_my.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class FamilyDetailActivity extends BaseActivity {

    ActivityFamilyDetailBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FamilyMember model;

    String doctorToken = "";
    String doctorName = "";
    String doctorGender = "";
    String specialize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFamilyDetailBinding.inflate(getLayoutInflater());
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

        initDropDownLists();

        binding.btnSend.setOnClickListener(view -> {
            sendOrder();
        });

        binding.tvHistory.setOnClickListener(view -> {
            startActivity(new Intent(this, TreatmentHistoryActivity.class)
                    .putExtra(Constants.TYPE_TITLE, Constants.TYPE_CUSTOMER)
                    .putExtra(Constants.TYPE_ID, model.getToken())
            );
        });
    }

    private void initDropDownLists() {

        ArrayList<String> gendersDoctorList = new ArrayList<>();
        gendersDoctorList.add("??????????????");
        gendersDoctorList.add("????????????");
        ArrayAdapter<String> gendersDoctorAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, gendersDoctorList);
        binding.etGenderDoctor.setAdapter(gendersDoctorAdapter);
        binding.etGenderDoctor.setThreshold(1);
        binding.etGenderDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
            doctorGender = (String) adapterView.getItemAtPosition(position);
            if (!doctorGender.isEmpty() && !specialize.isEmpty()) {
                loadDoctors();
            }
        });

        ArrayList<String> communicationList = new ArrayList<>();
        communicationList.add("??????????");
        communicationList.add("??????????");
        communicationList.add("??????????");
        ArrayAdapter<String> communicationAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, communicationList);
        binding.etCommunication.setAdapter(communicationAdapter);

        ArrayList<String> disabilityList = new ArrayList<>();
        disabilityList.add("??????????");
        disabilityList.add("???????????????? ??????????");
        disabilityList.add("???????????????? ????????????");
        disabilityList.add("????????????");
        disabilityList.add("???????????? ??????????");
        ArrayAdapter<String> disabilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disabilityList);
        binding.etSelectDisability.setAdapter(disabilityAdapter);
        binding.etSelectDisability.setOnItemClickListener((adapterView, view, position, l) -> {
            specialize = (String) adapterView.getItemAtPosition(position);
            if (!doctorGender.isEmpty() && !specialize.isEmpty()) {
                loadDoctors();
            }
        });

    }

    private void loadDoctors() {
        ArrayList<String> doctorList = new ArrayList<>();
        ArrayList<User> list = new ArrayList<>();
        db.collection("User")
                .whereEqualTo("type", "vendor")
                .whereEqualTo("gender", doctorGender)
                .whereEqualTo("specialize", specialize)
                .addSnapshotListener((value, error) -> {
                    list.clear();
                    doctorList.clear();
                    if (!Objects.requireNonNull(value).isEmpty()) {
                        for (QueryDocumentSnapshot document : value) {
                            User user = document.toObject(User.class);
                            doctorList.add(user.getName());
                            list.add(user);
                        }
                        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, doctorList);
                        binding.etDoctor.setAdapter(doctorAdapter);
                        binding.etDoctor.setText("");
                        binding.etDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
                            doctorToken = list.get(position).getToken();
                            doctorName = list.get(position).getName();
                        });
                        binding.tvDoctor.setEnabled(true);
                    } else {
                        binding.etDoctor.setAdapter(null);
                        binding.etDoctor.setText("");
                        binding.tvDoctor.setEnabled(false);
                        showOfflineAlert(this, "", "???? ???????? ???????????? ???????????? ???????????? ?????????? ?????? ");
                    }
                });
    }

    private void sendOrder() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
            Request order = new Request();
            order.setCommunicationType(getText(binding.etCommunication));
            order.setDisability(getText(binding.etSelectDisability));
            order.setDoctorGender(getText(binding.etGenderDoctor));
            order.setDoctorName(doctorName);
            order.setDoctorToken(doctorToken);
            order.setParentToken(model.getParent_token());
            order.setPatientToken(model.getToken());
            order.setPatientBirthday(model.getBirthday());
            order.setPatientName(model.getName());
            order.setPatientId(model.getId());
            order.setToken("");

            db.collection("Request")
                    .add(order)
                    .addOnSuccessListener(document -> {
                        document.update("token", document.getId());
                        enableElements(true);
                        showAlert(this, "", "???? ?????????? ?????????? ?????????????? ??????????????");

                        Constants.sendNotifications(
                                this,
                                "?????????? ???????????? : " + model.getName(),
                                "???????? ?????? ???????????? ????????",
                                doctorToken
                        );

                    });
        } else {
            Constants.showAlert(this, getString(R.string.no_internet), R.color.orange);
        }
    }

    private void enableElements(boolean enable) {
        binding.btnSend.setEnabled(enable);
        if (!enable) {
            binding.btnSend.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btnSend.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_accent));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
        binding.appbar.imgBack.setEnabled(enable);
        binding.etGenderDoctor.setEnabled(enable);
        binding.etSelectDisability.setEnabled(enable);
        binding.etCommunication.setEnabled(enable);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}