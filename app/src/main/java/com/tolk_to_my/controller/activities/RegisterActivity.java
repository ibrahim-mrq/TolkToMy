package com.tolk_to_my.controller.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityRegisterBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class RegisterActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityRegisterBinding binding;
    Calendar myCalendar = Calendar.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(Constants.USER_TYPE);

        binding.appbar.tvTool.setText(getString(R.string.register));
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        setUserType(this, type, binding.etType);
        if (type.equals(Constants.TYPE_VENDOR)) {
            initDropDownLists();
        }
        binding.etBirth.setOnClickListener(view -> {
            new DatePickerDialog(
                    this,
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        binding.btnLogin.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
                if (type.equals(Constants.TYPE_VENDOR)) {
                    registerVendor();
                } else {
                    registerCustomer();
                }
            } else {
                showOfflineAlert(this, "", getString(R.string.no_internet));
            }
        });

    }

    private void initDropDownLists() {
        ArrayList<String> list = new ArrayList<>();
        list.add("??????????");
        list.add("???????????????? ??????????");
        list.add("???????????????? ????????????");
        list.add("????????????");
        list.add("???????????? ??????????");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        binding.etSpecialize.setAdapter(adapter);
        binding.tvSpecialize.setVisibility(View.VISIBLE);

        ArrayList<String> genders = new ArrayList<>();
        genders.add("??????????????");
        genders.add("????????????");
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        binding.etGender.setAdapter(gendersAdapter);
        binding.tvGender.setVisibility(View.VISIBLE);
    }

    private void registerVendor() {
        if (isNotEmpty(binding.etName, binding.tvName)
                && isNotEmpty(binding.etSpecialize, binding.tvSpecialize)
                && isNotEmpty(binding.etGender, binding.tvGender)
                && isNotEmpty(binding.etId, binding.tvId)
                && isNotEmpty(binding.etPhone, binding.tvPhone)
                && isNotEmpty(binding.etBirth, binding.tvBirth)
                && isNotEmpty(binding.etEmail, binding.tvEmail)
                && isValidEmail(binding.etEmail, binding.tvEmail)
                && isNotEmpty(binding.etPassword, binding.tvPassword)
                && isNotEmpty(binding.etRePassword, binding.tvRePassword)
                && isPasswordLess(binding.etPassword, binding.tvPassword)
                && isNotEmpty(binding.etRePassword, binding.tvRePassword)
                && isPasswordLess(binding.etRePassword, binding.tvRePassword)
                && isPasswordMatch(binding.etPassword, binding.tvPassword, binding.etRePassword, binding.tvRePassword)
        ) {
            User user = new User();
            user.setName(getText(binding.etName));
            user.setSpecialize(getText(binding.etSpecialize));
            user.setId(getText(binding.etId));
            user.setPhone(getText(binding.etPhone));
            user.setBirthday(getText(binding.etBirth));
            user.setEmail(getText(binding.etEmail));
            user.setPassword(getText(binding.etPassword));
            user.setGender(getText(binding.etGender));
            user.setType(type);
            enableElements(false);
            auth.createUserWithEmailAndPassword(getText(binding.etEmail), getText(binding.etPassword))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.setToken(auth.getUid());
                            Constants.subscribeToTopic(Objects.requireNonNull(auth.getUid()));
                            db.collection("User")
                                    .document(Objects.requireNonNull(auth.getUid()))
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        showAlert(this, "", "???? ?????????? ???????? ???????? ??????????");
                                        Hawk.put(Constants.IS_LOGIN, true);
                                        Hawk.put(Constants.USER, user);
                                        Hawk.put(Constants.USER_TOKEN, auth.getUid());
                                        new Handler().postDelayed(() -> {
                                            enableElements(true);
                                            if (user.getType().equals(Constants.TYPE_CUSTOMER)) {
                                                Hawk.put(Constants.USER_TYPE, Constants.TYPE_CUSTOMER);
                                                startActivity(new Intent(this, FamilyActivity.class));
                                            } else {
                                                Hawk.put(Constants.USER_TYPE, Constants.TYPE_VENDOR);
                                                startActivity(new Intent(this, MainActivity.class));
                                            }
                                            finish();
                                        }, 2000);
                                    });
                        } else {
                            enableElements(true);
                            showErrorAlert(this, "???? ???????? ?????????? ????????", "???????? ???????????? ???? ?????? ???????? ????????????????");
                        }
                    });
        }
    }

    private void registerCustomer() {
        if (isNotEmpty(binding.etName, binding.tvName)
                && isNotEmpty(binding.etId, binding.tvId)
                && isNotEmpty(binding.etPhone, binding.tvPhone)
                && isNotEmpty(binding.etBirth, binding.tvBirth)
                && isNotEmpty(binding.etEmail, binding.tvEmail)
                && isValidEmail(binding.etEmail, binding.tvEmail)
                && isNotEmpty(binding.etPassword, binding.tvPassword)
                && isNotEmpty(binding.etRePassword, binding.tvRePassword)
                && isPasswordLess(binding.etPassword, binding.tvPassword)
                && isNotEmpty(binding.etRePassword, binding.tvRePassword)
                && isPasswordLess(binding.etRePassword, binding.tvRePassword)
                && isPasswordMatch(binding.etPassword, binding.tvPassword, binding.etRePassword, binding.tvRePassword)
        ) {
            User user = new User();
            user.setName(getText(binding.etName));
            user.setSpecialize("");
            user.setGender("");
            user.setId(getText(binding.etId));
            user.setPhone(getText(binding.etPhone));
            user.setBirthday(getText(binding.etBirth));
            user.setEmail(getText(binding.etEmail));
            user.setPassword(getText(binding.etPassword));
            user.setType(type);
            enableElements(false);
            auth.createUserWithEmailAndPassword(getText(binding.etEmail), getText(binding.etPassword))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            user.setToken(auth.getUid());
                            Constants.subscribeToTopic(Objects.requireNonNull(auth.getUid()));
                            db.collection("User")
                                    .document(Objects.requireNonNull(auth.getUid()))
                                    .set(user)
                                    .addOnSuccessListener(unused -> {
                                        showAlert(this, "", "???? ?????????? ???????? ???????? ??????????");
                                        Hawk.put(Constants.IS_LOGIN, true);
                                        Hawk.put(Constants.USER, user);
                                        Hawk.put(Constants.USER_TOKEN, auth.getUid());
                                        new Handler().postDelayed(() -> {
                                            enableElements(true);
                                            if (user.getType().equals(Constants.TYPE_CUSTOMER)) {
                                                Hawk.put(Constants.USER_TYPE, Constants.TYPE_CUSTOMER);
                                                startActivity(new Intent(this, FamilyActivity.class));
                                            } else {
                                                Hawk.put(Constants.USER_TYPE, Constants.TYPE_VENDOR);
                                                startActivity(new Intent(this, MainActivity.class));
                                            }
                                            finish();
                                        }, 2000);
                                    });
                        } else {
                            enableElements(true);
                            showErrorAlert(this, "???? ???????? ?????????? ????????", "???????? ???????????? ???? ?????? ???????? ????????????????");
                        }
                    });
        }
    }

    private void enableElements(boolean enable) {
        binding.btnLogin.setEnabled(enable);
        if (!enable) {
            binding.btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_grey));
            binding.progressBar.setVisibility(View.VISIBLE);
        } else {
            binding.btnLogin.setBackground(ContextCompat.getDrawable(this, R.drawable.shape_accent));
            binding.progressBar.setVisibility(View.INVISIBLE);
        }
        binding.appbar.imgBack.setEnabled(enable);
        binding.etName.setEnabled(enable);
        binding.etSpecialize.setEnabled(enable);
        binding.etId.setEnabled(enable);
        binding.etPhone.setEnabled(enable);
        binding.etEmail.setEnabled(enable);
        binding.etPassword.setEnabled(enable);
        binding.etRePassword.setEnabled(enable);
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