package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.controller.interfaces.DisabilityInterface;
import com.tolk_to_my.controller.interfaces.RemoveInterface;
import com.tolk_to_my.databinding.CustomDialogBinding;
import com.tolk_to_my.databinding.CustomSelectedBinding;

import java.util.ArrayList;

public class DisabilityAdapter extends RecyclerView.Adapter<DisabilityAdapter.CategorySubViewHolder> {

    Context mContext;
    ArrayList<String> list;
    DisabilityInterface anInterface;

    public DisabilityAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setList(ArrayList<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setAnInterface(DisabilityInterface anInterface) {
        this.anInterface = anInterface;
    }

    public ArrayList<String> getList() {
        return list;
    }

    public void remove(String category) {
        list.remove(category);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategorySubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_dialog, parent, false);
        return new CategorySubViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CategorySubViewHolder holder, int position) {
        String model = list.get(position);
        holder.bind(model);

        holder.itemView.setOnClickListener(view -> {
            anInterface.onclick(model);
        });

    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    public static class CategorySubViewHolder extends RecyclerView.ViewHolder {

        CustomDialogBinding binding;

        private CategorySubViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomDialogBinding.bind(itemView);
        }

        private void bind(String model) {
            binding.title.setText(model);
        }
    }
}