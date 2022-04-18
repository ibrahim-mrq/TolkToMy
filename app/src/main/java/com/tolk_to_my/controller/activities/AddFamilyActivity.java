package com.tolk_to_my.controller.activities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
    DisabilitySelectedAdapter selectedAdapter;
    ArrayList<String> localListDisability = new ArrayList<>();
    ArrayList<String> disability = new ArrayList<>();

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
        gendersList.add("ذكر");
        gendersList.add("انثى");
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gendersList);
        binding.etGender.setAdapter(gendersAdapter);

        disability.add("تخاطب");
        disability.add("اضطرابات نفسيه");
        disability.add("اضطرابات سلوكيه");
        disability.add("التوحد");
        disability.add("متلازم الدون");
        binding.etDisability.setOnClickListener(view -> {
            dialogDisability("تحديد نوع الاعاقة", disability);
        });


//        ArrayList<String> gendersDoctorList = new ArrayList<>();
//        gendersDoctorList.add("اخصائية");
//        gendersDoctorList.add("اخصائي");
//        ArrayAdapter<String> gendersDoctorAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gendersDoctorList);
//        binding.etGenderDoctor.setAdapter(gendersDoctorAdapter);
//        binding.etGenderDoctor.setThreshold(1);
//        binding.etGenderDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
//            doctorGender = (String) adapterView.getItemAtPosition(position);
//            if (!doctorGender.isEmpty() && !specialize.isEmpty()) {
//                loadDoctors();
//            }
//        });

//        ArrayAdapter<String> disabilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, disabilityList);
//        binding.etDisability.setAdapter(disabilityAdapter);
//        binding.etDisability.setOnItemClickListener((adapterView, view, position, l) -> {
//            specialize = (String) adapterView.getItemAtPosition(position);
//            if (!doctorGender.isEmpty() && !specialize.isEmpty()) {
//                loadDoctors();
//            }
//        });

    }

//    private void loadDoctors() {
//        ArrayList<String> doctorList = new ArrayList<>();
//        ArrayList<User> list = new ArrayList<>();
//        db.collection("User")
//                .whereEqualTo("type", "vendor")
//                .whereEqualTo("gender", doctorGender)
//                .whereEqualTo("specialize", specialize)
//                .addSnapshotListener((value, error) -> {
//                    list.clear();
//                    doctorList.clear();
//                    if (!Objects.requireNonNull(value).isEmpty()) {
//                        for (QueryDocumentSnapshot document : value) {
//                            User user = document.toObject(User.class);
//                            doctorList.add(user.getName());
//                            list.add(user);
//                        }
//                        ArrayAdapter<String> doctorAdapter = new ArrayAdapter<>(this,
//                                android.R.layout.simple_list_item_1, doctorList);
//                        binding.etDoctor.setAdapter(doctorAdapter);
//                        binding.etDoctor.setText("");
//                        binding.etDoctor.setOnItemClickListener((adapterView, view, position, l) -> {
//                            doctorToken = list.get(position).getToken();
//                        });
//                        binding.tvDoctor.setEnabled(true);
//                    } else {
//                        binding.etDoctor.setAdapter(null);
//                        binding.etDoctor.setText("");
//                        binding.tvDoctor.setEnabled(false);
//                        showOfflineAlert(this, "", "لا يوجد اطباء، الرجاء اختيار تصنيف اخر ");
//                    }
//                });
//    }

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
                disability.append("، ").append(localListDisability.get(i));
            }
            String aa = String.valueOf(disability.charAt(0));
            if (aa.equals("،"))
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
                        showAlert(this, "", "تم اضافة فرد جديد على العائلة");
                        DocumentReference reference = db.collection("FamilyMember")
                                .document(response.getId());
                        reference.update("token", response.getId());
                        new Handler().postDelayed(() -> {
                            enableElements(true);
                            onBackPressed();
                        }, 2000);
                    })
                    .addOnFailureListener(e -> {
                        showAlert(this, "", "هناك خطا ما !");
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