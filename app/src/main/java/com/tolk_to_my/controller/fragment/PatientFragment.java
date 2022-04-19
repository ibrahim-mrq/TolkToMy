package com.tolk_to_my.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.adapter.PatientAdapter;
import com.tolk_to_my.databinding.FragmentPatientBinding;
import com.tolk_to_my.helpers.BaseFragment;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PatientFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public PatientFragment() {
        // Required empty public constructor
    }

    public static PatientFragment newInstance() {
        PatientFragment fragment = new PatientFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentPatientBinding binding;
    PatientAdapter adapter;
    ArrayList<Patient> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientBinding.inflate(getLayoutInflater());
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

        adapter = new PatientAdapter(getActivity());
        binding.include.recyclerView.setAdapter(adapter);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.include.recyclerView.setHasFixedSize(true);
        loadData();

    }

    private void loadData() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(requireActivity())) {
            binding.include.statefulLayout.showLoading();
            binding.include.swipeToRefresh.setRefreshing(false);
            db.collection("Patient")
                    .whereEqualTo("doctorToken", Hawk.get(Constants.USER_TOKEN))
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(Patient.class));
                            }
                            if (list.isEmpty()) {
                                binding.include.statefulLayout.showEmpty();
                            } else {
                                binding.include.statefulLayout.showContent();
                            }
                            adapter.setList(list);
                        } else {
                            binding.include.statefulLayout.showError(
                                    getString(R.string.empty_data), view -> loadData());
                        }
                    });
        } else {
            binding.include.statefulLayout.showOffline(getString(R.string.no_internet), view -> {
                loadData();
            });
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }

}