package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.CustomTreatmentHistoryBinding;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.TreatmentHistory;

import java.util.ArrayList;

public class TreatmentHistoryAdapter extends RecyclerView.Adapter<TreatmentHistoryAdapter.PatientAdapterViewHolder> {

    Context mContext;
    ArrayList<TreatmentHistory> list;

    public TreatmentHistoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<TreatmentHistory> getList() {
        return list;
    }

    public void setList(ArrayList<TreatmentHistory> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PatientAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_treatment_history, parent, false);
        return new PatientAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientAdapterViewHolder holder, int position) {
        TreatmentHistory model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class PatientAdapterViewHolder extends RecyclerView.ViewHolder {

        CustomTreatmentHistoryBinding binding;

        private PatientAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomTreatmentHistoryBinding.bind(itemView);
        }

        private void bind(TreatmentHistory model) {

            binding.name.setText(Constants.setText(mContext, R.string.name, model.getPatientName()));
            binding.birthday.setText(Constants.setText(mContext, R.string.birth, model.getPatientBirthday()));
            binding.disability.setText(Constants.setText(mContext, R.string.disability, model.getDisability()));
            binding.id.setText(Constants.setText(mContext, R.string.id, model.getPatientId()));

            binding.height.setText(Constants.setText(mContext, R.string.height, model.getHeight()));
            binding.heart.setText(Constants.setText(mContext, R.string.heart, model.getHeart()));
            binding.pressure.setText(Constants.setText(mContext, R.string.pressure, model.getPressure()));
            binding.sugar.setText(Constants.setText(mContext, R.string.sugar, model.getSugar()));
            binding.temperature.setText(Constants.setText(mContext, R.string.temperature, model.getTemperature()));
            binding.weight.setText(Constants.setText(mContext, R.string.weight, model.getWeight()));

            binding.date.setText(Constants.setText(mContext, R.string.create_data, model.getCreateData()));

        }
    }

}
