package com.tolk_to_my.controller.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityLoginBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.User;

import java.util.Objects;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        type = getIntent().getStringExtra(Constants.USER_TYPE);

        setTypeLogin(this, type, binding.appbar.tvTool);
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        binding.btnLogin.setOnClickListener(view -> {
            if (NetworkHelper.INSTANCE.isNetworkOnline(this)) {
                login();
            } else {
                showOfflineAlert(this, "", getString(R.string.no_internet));
            }
        });
        binding.tvRegister.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class)
                    .putExtra(Constants.USER_TYPE, type)
            );
        });

    }

    private void login() {
        if (isNotEmpty(binding.etEmail, binding.tvEmail)
                && isValidEmail(binding.etEmail, binding.tvEmail)
                && isNotEmpty(binding.etPassword, binding.tvPassword)
                && isPasswordLess(binding.etPassword, binding.tvPassword)
        ) {
            enableElements(false);
            auth.signInWithEmailAndPassword(getText(binding.etEmail), getText(binding.etPassword))
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            getUserData(auth.getUid());
                        } else {
                            enableElements(true);
                            showErrorAlert(this, "هناك خطا ما", "يرجى التحقق من البيانات");
                        }
                    });
        }
    }

    private void getUserData(String token) {
        db.collection("User")
                .document(token)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        User user = document.toObject(User.class);
                        assert user != null;
                        if (user.getType().equals(type)) {
                            showAlert(this, "", "تم تسجيل الدحول بنجاح");
                            Hawk.put(Constants.IS_LOGIN, true);
                            Hawk.put(Constants.USER, user);
                            Hawk.put(Constants.USER_ID, Objects.requireNonNull(auth.getCurrentUser()).getUid());
                            new Handler().postDelayed(() -> {
                                enableElements(true);
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }, 2000);
                        } else {
                            enableElements(true);
                            showErrorAlert(this, "هناك خطا ما", "يرجى التحقق نوع تسجيل الدخول");
                        }
                    }
                });
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
        binding.etEmail.setEnabled(enable);
        binding.etPassword.setEnabled(enable);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}