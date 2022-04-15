package com.tolk_to_my.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tolk_to_my.controller.activities.AddFamilyActivity;
import com.tolk_to_my.databinding.FragmentFamilyBinding;
import com.tolk_to_my.helpers.BaseFragment;

import org.jetbrains.annotations.NotNull;

public class FamilyFragment extends BaseFragment {

    public FamilyFragment() {
        // Required empty public constructor
    }

    public static FamilyFragment newInstance() {
        FamilyFragment fragment = new FamilyFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentFamilyBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFamilyBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddFamilyActivity.class));
        });
    }
}