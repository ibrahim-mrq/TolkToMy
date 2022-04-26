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
import com.tolk_to_my.controller.fragment.FamilyFragment;
import com.tolk_to_my.controller.fragment.VideoFragment;
import com.tolk_to_my.databinding.ActivityFamilyBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.FamilyMember;
import com.tolk_to_my.model.Video;

import java.util.ArrayList;

@SuppressLint("StaticFieldLeak")
public class FamilyActivity extends BaseActivity {

    public static ActivityFamilyBinding binding;
    public static Context context;
    public static FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFamilyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        context = FamilyActivity.this;
        setSupportActionBar(binding.appbar.toolbar);
        binding.appbar.tvTool.setText(getString(R.string.family));
        bottomNavigation();
        updateFamilyBadge();
        updateVideoBadge();
    }

    @SuppressLint("NonConstantResourceId")
    private void bottomNavigation() {
        replaceFragment(FamilyFragment.newInstance(), R.string.family);
        binding.main.bottomNavigation.getMenu().clear();
        binding.main.bottomNavigation.inflateMenu(R.menu.menu_bottom_customer);
        binding.main.bottomNavigation.setSelectedItemId(R.id.home);
        binding.main.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.family:
                    replaceFragment(FamilyFragment.newInstance(), R.string.family);
                    return true;
                case R.id.medical_awareness:
                    replaceFragment(VideoFragment.newInstance(), R.string.medical_awareness);
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

    public static void updateFamilyBadge() {
        BadgeDrawable familyBadge = binding.main.bottomNavigation.getOrCreateBadge(R.id.family);
        ArrayList<FamilyMember> list = new ArrayList<>();
        if (NetworkHelper.INSTANCE.isNetworkOnline(context)) {
            db.collection("FamilyMember")
                    .whereEqualTo("parent_token", Hawk.get(Constants.USER_TOKEN))
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            familyBadge.setVisible(true);
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(FamilyMember.class));
                            }
                            if (list.isEmpty()) {
                                familyBadge.setNumber(0);
                            } else {
                                familyBadge.setNumber(list.size());
                            }
                        } else {
                            familyBadge.setVisible(false);
                            familyBadge.setNumber(0);
                        }
                    });
        } else {
            familyBadge.setVisible(false);
            familyBadge.setNumber(0);
        }
    }

    public static void updateVideoBadge() {
        BadgeDrawable videoBadge = binding.main.bottomNavigation.getOrCreateBadge(R.id.medical_awareness);
        ArrayList<Video> list = new ArrayList<>();
        if (NetworkHelper.INSTANCE.isNetworkOnline(context)) {
            db.collection("Video")
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            videoBadge.setVisible(true);
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(Video.class));
                            }
                            if (list.isEmpty()) {
                                videoBadge.setNumber(0);
                            } else {
                                videoBadge.setNumber(list.size());
                            }
                        } else {
                            videoBadge.setVisible(false);
                            videoBadge.setNumber(0);
                        }
                    });
        } else {
            videoBadge.setVisible(false);
            videoBadge.setNumber(0);
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