package com.tolk_to_my.controller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.adapter.DisabilityAdapter;
import com.tolk_to_my.controller.adapter.DisabilitySelectedAdapter;
import com.tolk_to_my.databinding.ActivityAddFamilyBinding;
import com.tolk_to_my.databinding.CustomDialogListBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.FamilyMember;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddFamilyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    ActivityAddFamilyBinding binding;
    Calendar myCalendar = Calendar.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DisabilitySelectedAdapter selectedAdapter;
    ArrayList<String> localListDisability = new ArrayList<>();
    ArrayList<String> disability = new ArrayList<>();

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

        binding.uploadDisability.setVisibility(View.GONE);
        selectedAdapter = new DisabilitySelectedAdapter(this);
        selectedAdapter.setRemoveInterface((model, position) -> {
            disability.add(model);
            localListDisability.remove(position);
            if (localListDisability.isEmpty()) {
                binding.uploadDisability.setVisibility(View.GONE);
            } else {
                binding.uploadDisability.setVisibility(View.VISIBLE);
            }
            selectedAdapter.setList(localListDisability);
        });
        binding.recyclerView.setAdapter(selectedAdapter);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        binding.etBirth.setOnClickListener(view -> {
            new DatePickerDialog(
                    this,
                    this,
                    myCalendar.get(Calendar.YEAR),
                    myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
                    .show();
        });

        binding.btnSave.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
                addFamilyMember();
            } else {
                Constants.showAlert(this, getString(R.string.no_internet), R.color.orange);
            }
        });

    }

    private void initDropDownLists() {

        ArrayList<String> gendersList = new ArrayList<>();
        gendersList.add("??????");
        gendersList.add("????????");
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gendersList);
        binding.etGender.setAdapter(gendersAdapter);

        disability.add("??????????");
        disability.add("???????????????? ??????????");
        disability.add("???????????????? ????????????");
        disability.add("????????????");
        disability.add("???????????? ??????????");
        binding.etDisability.setOnClickListener(view -> {
            dialogDisability("?????????? ?????? ??????????????", disability);
        });

    }

    private void addFamilyMember() {
        if (isNotEmpty(binding.etName, binding.tvName)
                && isNotEmpty(binding.etId, binding.tvId)
                && isNotEmpty(binding.etAge, binding.tvAge)
                && isNotEmpty(binding.etBirth, binding.tvBirth)
                && isNotEmpty(binding.etGender, binding.tvGender)
                && isListNotEmpty(localListDisability, binding.tvDisability)
        ) {

            StringBuilder disability = new StringBuilder();
            for (int i = 0; i < localListDisability.size(); i++) {
                disability.append("?? ").append(localListDisability.get(i));
            }
            String aa = String.valueOf(disability.charAt(0));
            if (aa.equals("??"))
                disability.replace(0, 1, "");

            FamilyMember model = new FamilyMember();
            model.setAge(getText(binding.etAge));
            model.setBirthday(getText(binding.etBirth));
            model.setDisability(disability.toString().trim());
            model.setGender(getText(binding.etGender));
            model.setId(getText(binding.etId));
            model.setName(getText(binding.etName));
            model.setParent_token(Hawk.get(Constants.USER_TOKEN));
            model.setToken("");

            enableElements(false);
            db.collection("FamilyMember")
                    .add(model)
                    .addOnSuccessListener(response -> {
                        showAlert(this, "", "???? ?????????? ?????? ???????? ?????? ??????????????");
                        response.update("token", response.getId());
                        new Handler().postDelayed(() -> {
                            enableElements(true);
                            onBackPressed();
                        }, 2000);
                    })
                    .addOnFailureListener(e -> {
                        showAlert(this, "", "???????? ?????? ???? !");
                        enableElements(true);
                    });
        }
    }

    private void dialogDisability(String title, ArrayList<String> list) {
        Dialog dialog = new Dialog(this);
        CustomDialogListBinding dialogBinding = CustomDialogListBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        dialogBinding.tvTitle.setText(title);
        DisabilityAdapter adapter = new DisabilityAdapter(this);
        adapter.setAnInterface(model -> {
            localListDisability.add(model);
            adapter.remove(model);
            if (localListDisability.isEmpty()) {
                binding.uploadDisability.setVisibility(View.GONE);
            } else {
                binding.uploadDisability.setVisibility(View.VISIBLE);
            }
            selectedAdapter.setList(localListDisability);
            dialog.dismiss();
        });
        dialogBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialogBinding.recyclerView.setHasFixedSize(false);
        dialogBinding.recyclerView.setAdapter(adapter);
        adapter.setList(list);

        if (list.isEmpty()) {
            dialogBinding.statefulLayout.showEmpty();
        } else {
            dialogBinding.statefulLayout.showContent();
        }
        dialog.show();
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