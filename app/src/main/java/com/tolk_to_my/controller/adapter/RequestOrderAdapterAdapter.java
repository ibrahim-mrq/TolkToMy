package com.tolk_to_my.controller.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.CustomOrderBinding;
import com.tolk_to_my.model.Order;

import java.util.ArrayList;

public class RequestOrderAdapterAdapter extends RecyclerView.Adapter<RequestOrderAdapterAdapter.RequestOrderAdapterViewHolder> {

    Context mContext;
    ArrayList<Order> list;

    public RequestOrderAdapterAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Order> getList() {
        return list;
    }

    public void setList(ArrayList<Order> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RequestOrderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_order, parent, false);
        return new RequestOrderAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestOrderAdapterViewHolder holder, int position) {
        Order model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    static class RequestOrderAdapterViewHolder extends RecyclerView.ViewHolder {

        CustomOrderBinding binding;

        private RequestOrderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomOrderBinding.bind(itemView);
        }

        @SuppressLint("SetTextI18n")
        private void bind(Order model) {

        }
    }

}
