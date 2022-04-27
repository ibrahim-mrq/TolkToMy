package com.tolk_to_my.controller.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.orhanobut.hawk.Hawk;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.activities.MainActivity;
import com.tolk_to_my.controller.adapter.RequestAdapter;
import com.tolk_to_my.controller.interfaces.RequestInterface;
import com.tolk_to_my.databinding.FragmentRequestBinding;
import com.tolk_to_my.firebase.ApiRequest;
import com.tolk_to_my.firebase.Results;
import com.tolk_to_my.helpers.BaseFragment;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.helpers.NetworkHelper;
import com.tolk_to_my.model.Patient;
import com.tolk_to_my.model.Request;
import com.tolk_to_my.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

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
        new ApiRequest<User>().getData(
                requireActivity(),
                "User",
                User.class,
                new Results<ArrayList<User>>() {
                    @Override
                    public void onSuccess(ArrayList<User> users) {
                        Log.e("response", "users = " + users);
                    }

                    @Override
                    public void onFailureInternet(@NotNull String message) {

                    }

                    @Override
                    public void onEmpty() {

                    }
                }
        );
        Log.e("response", "========================================= \n\n\n\n\n\n\n\n");

        new ApiRequest<Request>().getData(
                requireActivity(),
                "Request",
                Request.class,
                new Results<ArrayList<Request>>() {
                    @Override
                    public void onSuccess(ArrayList<Request> requests) {
                        Log.e("response", "requests = " + requests);
                    }

                    @Override
                    public void onFailureInternet(@NotNull String message) {
                        Log.e("response", "requests = " + message);
                    }

                    @Override
                    public void onEmpty() {
                        Log.e("response", "onEmpty = ");
                    }
                }
        );

        new ApiRequest<FamilyFragment>();
    }

    private void initView() {
        binding.include.swipeToRefresh.setOnRefreshListener(this);
        binding.include.swipeToRefresh.setRefreshing(false);

        adapter = new RequestAdapter(getActivity());
        adapter.setRequestInterface(new RequestInterface() {
            @Override
            public void accept(Request model, int position) {
                acceptOrder(model, position);
            }

            @Override
            public void delete(Request model) {
                deleteOrder(model);
            }
        });
        binding.include.recyclerView.setAdapter(adapter);
        binding.include.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.include.recyclerView.setHasFixedSize(true);
        loadData();
        MainActivity.updateRequestBadge();

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

    private void acceptOrder(Request model, int position) {
        if (NetworkHelper.INSTANCE.isNetworkOnline(requireActivity())) {
            Patient patient = new Patient();
            patient.setCommunicationType(model.getCommunicationType());
            patient.setDisability(model.getDisability());
            patient.setDoctorGender(model.getDoctorGender());
            patient.setDoctorName(model.getDoctorName());
            patient.setDoctorToken(model.getDoctorToken());
            patient.setFirstTime(true);
            patient.setParentToken(model.getParentToken());
            patient.setPatientBirthday(model.getPatientBirthday());
            patient.setPatientName(model.getPatientName());
            patient.setPatientId(model.getPatientId());
            patient.setPatientId(model.getPatientId());
            patient.setPatientToken(model.getPatientToken());

            patient.setToken("");

            showCustomProgress(false);
            db.collection("Patient")
                    .add(patient)
                    .addOnSuccessListener(document -> {
                        db.collection("Request").document(model.getToken()).delete();
                        document.update("token", document.getId());
                        dismissCustomProgress();
                        list.remove(position);
                        adapter.setList(list);
                        showAlert("", "تم قبول المريض");
                        MainActivity.updateRequestBadge();

                        Constants.sendNotifications(
                                requireActivity(),
                                "الدكتور : " + model.getDoctorName(),
                                "تم الموافقة على الجلسة العلاجية",
                                model.getParentToken()
                        );
                    });
        } else {
            Constants.showAlert(requireActivity(), getString(R.string.no_internet), R.color.orange);
        }
    }

    private void deleteOrder(Request model) {
        if (NetworkHelper.INSTANCE.isNetworkOnline(requireActivity())) {
            showCustomProgress(false);
            db.collection("Request")
                    .document(model.getToken())
                    .delete()
                    .addOnSuccessListener(unused -> {
                        dismissCustomProgress();
                        showOfflineAlert("", "تم رفض المريض");
                        MainActivity.updateRequestBadge();
                        Constants.sendNotifications(
                                requireActivity(),
                                "الدكتور : " + model.getDoctorName(),
                                "تم رفض الجلسة العلاجية",
                                model.getParentToken()
                        );
                    });
        } else {
            Constants.showAlert(requireActivity(), getString(R.string.no_internet), R.color.orange);
        }
    }

    @Override
    public void onRefresh() {
        loadData();
    }
}