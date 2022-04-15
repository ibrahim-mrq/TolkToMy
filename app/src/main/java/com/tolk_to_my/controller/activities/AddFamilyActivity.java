package com.tolk_to_my.controller.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityAddFamilyBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.model.User;

import java.util.ArrayList;
import java.util.Objects;

public class AddFamilyActivity extends BaseActivity {

    ActivityAddFamilyBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String doctorToken = "";
    String doctorGender = "";
    String specialize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.appbar.tvTool.setText(getString(R.string.family));
        initSpecialize();
    }

    private void initSpecialize() {

        ArrayList<String> gendersList = new ArrayList<>();
        gendersList.add("ذكر");
        gendersList.add("انثى");
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gendersList);
        binding.etGender.setAdapter(gendersAdapter);

        ArrayList<String> gendersDoctorList = new ArrayList<>();
        gendersDoctorList.add("اخصائية");
        gendersDoctorList.add("اخصائي");
        ArrayAdapter<String> gendersDoctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gendersDoctorList);
        binding.etGenderDoctor.setAdapter(gendersDoctorAdapter);
        binding.etGenderDoctor.setThreshold(1);
        binding.etGenderDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
            doctorGender = (String) adapterView.getItemAtPosition(position);
            loadDoctors();
        });
        ArrayList<String> disabilityList = new ArrayList<>();
        disabilityList.add("تخاطب");
        disabilityList.add("اضطرابات نفسبه");
        disabilityList.add("اضطرابات سلوكيه");
        disabilityList.add("التوحد");
        disabilityList.add("متلازم الدون");
        ArrayAdapter<String> disabilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disabilityList);
        binding.etDisability.setAdapter(disabilityAdapter);
        binding.etDisability.setOnItemClickListener((adapterView, view, position, l) -> {
            specialize = (String) adapterView.getItemAtPosition(position);
            loadDoctors();
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
                            User user = (User) document.toObject(User.class);
                            doctorList.add(user.getName());
                            list.add(user);
                        }
                        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this,
                                android.R.layout.simple_list_item_1, doctorList);
                        binding.etDoctor.setAdapter(doctorAdapter);
                        binding.etDoctor.setText("");
                        binding.etDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
                            doctorToken = list.get(position).getToken();
                        });
                        binding.tvDoctor.setEnabled(true);
                    } else {
                        binding.etDoctor.setAdapter(null);
                        binding.etDoctor.setText("");
                        showOfflineAlert(this, "", "لا يوجد اطباء، الرجاء اختيار تصنيف اخر ");
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}