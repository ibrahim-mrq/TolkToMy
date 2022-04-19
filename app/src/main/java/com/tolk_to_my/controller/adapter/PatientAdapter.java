package com.tolk_to_my.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.controller.activities.PatientDetailActivity;
import com.tolk_to_my.controller.activities.VitalSignsActivity;
import com.tolk_to_my.databinding.CustomPatientBinding;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.Patient;

import java.util.ArrayList;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientAdapterViewHolder> {

    Context mContext;
    ArrayList<Patient> list;

    public PatientAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Patient> getList() {
        return list;
    }

    public void setList(ArrayList<Patient> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_patient, parent, false);
        return new PatientAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapterViewHolder holder, int position) {
        Patient model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class PatientAdapterViewHolder extends RecyclerView.ViewHolder {

        CustomPatientBinding binding;

        private PatientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomPatientBinding.bind(itemView);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Patient model) {

            binding.name.setText(Constants.setText(mContext, R.string.name, model.getPatientName()));
            binding.birthday.setText(Constants.setText(mContext, R.string.birth, model.getPatientBirthday()));
            binding.disability.setText(Constants.setText(mContext, R.string.disability, model.getDisability()));
            if (model.isFirstTime()) {
                binding.isNew.setText(mContext.getString(R.string.patient) + " " + mContext.getString(R.string.neww));
                binding.isNew.setTextColor(mContext.getResources().getColor(R.color.green_success));
            } else {
                binding.isNew.setText(mContext.getString(R.string.patient) + " " + mContext.getString(R.string.old));
                binding.isNew.setTextColor(mContext.getResources().getColor(R.color.orange));
            }

            itemView.setOnClickListener(view -> {
                if (model.isFirstTime()) {
                    mContext.startActivity(new Intent(mContext, VitalSignsActivity.class)
                            .putExtra(Constants.TYPE_MODEL, model));
                } else {
                    mContext.startActivity(new Intent(mContext, PatientDetailActivity.class)
                            .putExtra(Constants.TYPE_MODEL, model));
                }
            });

        }
    }

}
