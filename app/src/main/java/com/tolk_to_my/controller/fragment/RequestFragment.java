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
import com.tolk_to_my.controller.adapter.RequestAdapter;
import com.tolk_to_my.controller.interfaces.RequestInterface;
import com.tolk_to_my.databinding.FragmentRequestBinding;
import com.tolk_to_my.helpers.BaseFragment;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;
import com.tolk_to_my.model.Request;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class RequestFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    public RequestFragment() {
        // Required empty public constructor
    }

    public static RequestFragment newInstance() {
        RequestFragment fragment = new RequestFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    FragmentRequestBinding binding;
    RequestAdapter adapter;
    ArrayList<Request> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRequestBinding.inflate(getLayoutInflater());
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

        adapter = new RequestAdapter(getActivity());
        adapter.setRequestInterface(new RequestInterface() {
            @Override
            public void accept(Request model, int position) {
                acceptOrder();
            }

            @Override
            public void delete(Request model, int position) {

            }
        });
        binding.include.recyclerView.setAdapter(adapter);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.include.recyclerView.setHasFixedSize(true);
        loadData();

    }

    private void loadData() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(requireActivity())) {
            binding.include.statefulLayout.showLoading();
            binding.include.swipeToRefresh.setRefreshing(false);
            db.collection("Request")
                    .whereEqualTo("doctorToken", Hawk.get(Constants.USER_TOKEN))
                    .addSnapshotListener((query, error) -> {
                        list.clear();
                        if (query != null) {
                            for (QueryDocumentSnapshot document : query) {
                                list.add(document.toObject(Request.class));
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

    private void acceptOrder() {
        if (NetworkHelper.INSTANCE.isNetworkOnline(requireActivity())) {
            Patient order = new Patient();

//            enableElements(false);
//            db.collection("Patient")
//                    .add(order)
//                    .addOnSuccessListener(document -> {
//                        document.update("token", document.getId());
//                        enableElements(true);
//                        enableSendElements(false);
//                        showAlert("", "تم ارسال الطلب للدكتور المعالج");
//                    });
        } else {
            Constants.showAlert(requireActivity(), getString(R.string.no_internet), R.color.orange);
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}