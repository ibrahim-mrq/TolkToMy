package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.controller.interfaces.RequestInterface;
import com.tolk_to_my.databinding.CustomRequestBinding;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.Request;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestOrderAdapterViewHolder> {

    Context mContext;
    ArrayList<Request> list;
    RequestInterface requestInterface;

    public RequestAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Request> getList() {
        return list;
    }

    public void setList(ArrayList<Request> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setRequestInterface(RequestInterface requestInterface) {
        this.requestInterface = requestInterface;
    }

    @NonNull
    @Override
    public RequestOrderAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_request, parent, false);
        return new RequestOrderAdapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestOrderAdapterViewHolder holder, int position) {
        Request model = list.get(position);
        holder.bind(model);

        holder.binding.btnYes.setOnClickListener(view -> {
            requestInterface.accept(model, position);
        });

        holder.binding.btnNo.setOnClickListener(view -> {
            requestInterface.delete(model);
        });
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class RequestOrderAdapterViewHolder extends RecyclerView.ViewHolder {

        CustomRequestBinding binding;

        private RequestOrderAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomRequestBinding.bind(itemView);
        }

        private void bind(Request model) {
            binding.name.setText(Constants.setText(mContext, R.string.name, model.getPatientName()));
            binding.birthday.setText(Constants.setText(mContext, R.string.birth, model.getPatientBirthday()));
            binding.disability.setText(Constants.setText(mContext, R.string.disability, model.getDisability()));
        }
    }

}
