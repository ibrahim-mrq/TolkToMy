package com.tolk_to_my.controller.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.badge.BadgeDrawable;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.fragment.PatientFragment;
import com.tolk_to_my.controller.fragment.RequestFragment;
import com.tolk_to_my.databinding.ActivityMainBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;
import com.tolk_to_my.model.Request;

import java.util.ArrayList;

@SuppressLint("StaticFieldLeak")
public class MainActivity extends BaseActivity {

    public static ActivityMainBinding binding;
    public static Context context;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        context = MainActivity.this;
        setSupportActionBar(binding.appbar.toolbar);
        binding.appbar.tvTool.setText(getString(R.string.app_name));
        bottomNavigation();
        updateRequestBadge();
        updatePatientBadge();
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigation() {
        replaceFragment(PatientFragment.newInstance(), R.string.reviews);
        binding.main.bottomNavigation.getMenu().clear();
        binding.main.bottomNavigation.inflateMenu(R.menu.menu_bottom_vendor);
        binding.main.bottomNavigation.setSelectedItemId(R.id.home);
        binding.main.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.reviews:
                    replaceFragment(PatientFragment.newInstance(), R.string.reviews);
                    return true;
                case R.id.review_request:
                    replaceFragment(RequestFragment.newInstance(), R.string.review_request);
                    return true;
            }
            return false;
        });
    }

    public void replaceFragment(Fragment fragment, @StringRes int title) {
        binding.appbar.tvTool.setText(getResources().getText(title));
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame, fragment);
        transaction.commit();
    }

    public static void updateRequestBadge() {
        BadgeDrawable requestBadge = binding.main.bottomNavigation.getOrCreateBadge(R.id.review_request);
        ArrayList<Request> list = new ArrayList<>();
        if (NetworkHelper.INSTANCE.isNetworkOnline(context)) {
            db.collection("Request")
                    .whereEqualTo("doctorToken", Hawk.get(Constants.USER_TOKEN))
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            requestBadge.setVisible(true);
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(Request.class));
                            }
                            if (list.isEmpty()) {
                                requestBadge.setNumber(0);
                            } else {
                                requestBadge.setNumber(list.size());
                            }
                        } else {
                            requestBadge.setVisible(false);
                            requestBadge.setNumber(0);
                        }
                    });
        } else {
            requestBadge.setVisible(false);
            requestBadge.setNumber(0);
        }
    }

    public static void updatePatientBadge() {
        BadgeDrawable reviewsBadge = binding.main.bottomNavigation.getOrCreateBadge(R.id.reviews);
        ArrayList<Patient> list = new ArrayList<>();
        if (NetworkHelper.INSTANCE.isNetworkOnline(context)) {
            db.collection("Patient")
                    .whereEqualTo("doctorToken", Hawk.get(Constants.USER_TOKEN))
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            reviewsBadge.setVisible(true);
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(Patient.class));
                            }
                            if (list.isEmpty()) {
                                reviewsBadge.setNumber(0);
                            } else {
                                reviewsBadge.setNumber(list.size());
                            }
                        } else {
                            reviewsBadge.setVisible(false);
                            reviewsBadge.setNumber(0);
                        }
                    });
        } else {
            reviewsBadge.setVisible(false);
            reviewsBadge.setNumber(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_appbar, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.appbar_profile:
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            case R.id.appbar_setting:
                startActivity(new Intent(this, SettingActivity.class));
                return true;
            case R.id.appbar_logout:
                Constants.logout(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Toast backToasty;
    private long backPressedTime;

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1)
            getSupportFragmentManager().popBackStack();
        else {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                backToasty.cancel();
                super.onBackPressed();
                return;
            } else {
                backToasty = Toast.makeText(this, getString(R.string.back_exit), Toast.LENGTH_SHORT);
                backToasty.show();
            }
            backPressedTime = System.currentTimeMillis();
        }
    }
}