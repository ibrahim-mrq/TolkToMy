package com.tolk_to_my.controller.activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityAddFamilyBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.FamilyMember;
import com.tolk_to_my.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class AddFamilyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityAddFamilyBinding binding;
    Calendar myCalendar = Calendar.getInstance();
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
        binding.appbar.tvTool.setText(getString(R.string.add_family));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());
        initDropDownLists();

        binding.etBirth.setOnClickListener(view -> {
            new DatePickerDialog(
                    this,
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        binding.btnSave.setOnClickListener(view -> addFamilyMember());

    }

    private void initDropDownLists() {

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
            if (!doctorGender.isEmpty() && !specialize.isEmpty()) {
                loadDoctors();
            }
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
                        });
                        binding.tvDoctor.setEnabled(true);
                    } else {
                        binding.etDoctor.setAdapter(null);
                        binding.etDoctor.setText("");
                        binding.tvDoctor.setEnabled(false);
                        showOfflineAlert(this, "", "لا يوجد اطباء، الرجاء اختيار تصنيف اخر ");
                    }
                });
    }

    private void addFamilyMember() {
        if (isNotEmpty(binding.etName, binding.tvName)
                && isNotEmpty(binding.etId, binding.tvId)
                && isNotEmpty(binding.etAge, binding.tvAge)
                && isNotEmpty(binding.etBirth, binding.tvBirth)
                && isNotEmpty(binding.etGender, binding.tvGender)
                && isNotEmpty(binding.etDisability, binding.tvDisability)
                && isNotEmpty(binding.etGenderDoctor, binding.tvGenderDoctor)
                && isNotEmpty(binding.etDoctor, binding.tvDoctor)
        ) {
            enableElements(false);
            FamilyMember model = new FamilyMember();
            model.setAge(getText(binding.etAge));
            model.setBirthday(getText(binding.etBirth));
            model.setDisability(getText(binding.etDisability));
            model.setDoctor(getText(binding.etDoctor));
            model.setGender(getText(binding.etGender));
            model.setGender_doctor(getText(binding.etGenderDoctor));
            model.setId(getText(binding.etId));
            model.setName(getText(binding.etName));
            model.setDoctor_token(doctorToken);
            model.setParent_token(Hawk.get(Constants.USER_ID));
            model.setToken("");
            db.collection("FamilyMember")
                    .add(model)
                    .addOnSuccessListener(response -> {
                        showAlert(this, "", "تم اضافة فرد جديد على العائلة");
                        DocumentReference reference = db.collection("FamilyMember")
                                .document(response.getId());
                        reference.update("token", response.getId());
                        new Handler().postDelayed(() -> {
                            enableElements(true);
                            onBackPressed();
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
        binding.etName.setEnabled(enable);
        binding.etId.setEnabled(enable);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        myCalendar.set(Calendar.YEAR, year);
        myCalendar.set(Calendar.MONTH, month);
        myCalendar.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        binding.etBirth.setText(dateFormat.format(myCalendar.getTime()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}