package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.tolk_to_my.R;
import com.tolk_to_my.controller.interfaces.RemoveInterface;
import com.tolk_to_my.databinding.CustomSelectedBinding;

import java.util.ArrayList;

public class DisabilitySelectedAdapter extends RecyclerView.Adapter<DisabilitySelectedAdapter.CategorySubViewHolder> {

    Context mContext;
    ArrayList<String> list;
    RemoveInterface removeInterface;

    public DisabilitySelectedAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setRemoveInterface(RemoveInterface removeInterface) {
        this.removeInterface = removeInterface;
    }

    public ArrayList<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public CategorySubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_selected, parent, false);
        return new CategorySubViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySubViewHolder holder, int position) {
        String model = list.get(position);
        holder.bind(model);

        holder.binding.image.setOnClickListener(v -> {
            removeInterface.remove(model, position);
        });

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public static class CategorySubViewHolder extends RecyclerView.ViewHolder {

        CustomSelectedBinding binding;

        private CategorySubViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomSelectedBinding.bind(itemView);
        }

        private void bind(String model) {
            binding.title.setText(model);
        }
    }
}