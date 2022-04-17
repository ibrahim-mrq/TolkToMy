package com.tolk_to_my.controller.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.activities.AddFamilyActivity;
import com.tolk_to_my.controller.adapter.FamilyMemberAdapter;
import com.tolk_to_my.databinding.FragmentFamilyBinding;
import com.tolk_to_my.helpers.BaseFragment;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.FamilyMember;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FamilyFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

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
    FamilyMemberAdapter adapter;
    ArrayList<FamilyMember> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        binding.include.swipeToRefresh.setOnRefreshListener(this);
        binding.include.swipeToRefresh.setRefreshing(false);

        adapter = new FamilyMemberAdapter(getActivity());
        binding.include.recyclerView.setAdapter(adapter);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.include.recyclerView.setHasFixedSize(true);
        loadData();
        binding.fab.setOnClickListener(view -> {
            startActivity(new Intent(getActivity(), AddFamilyActivity.class));
        });
    }

    private void loadData() {
        binding.include.statefulLayout.showLoading();
        binding.include.swipeToRefresh.setRefreshing(false);
        db.collection("FamilyMember")
                .whereEqualTo("parent_token", Hawk.get(Constants.USER_TOKEN))
                .get()
                .addOnCompleteListener(task -> {
                    list.clear();
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot document : task.getResult()) {
                            list.add(document.toObject(FamilyMember.class));
                        }
                        if (list.isEmpty()) {
                            binding.include.statefulLayout.showEmpty();
                        } else {
                            binding.include.statefulLayout.showContent();
                            adapter.setList(list);
                        }
                    } else {
                        binding.include.statefulLayout.showError(getString(R.string.empty_data), view -> {
                            loadData();
                        });
                    }
                });
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}