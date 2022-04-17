package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tolk_to_my.R;
import com.tolk_to_my.databinding.CustomFamilyMemberBinding;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.FamilyMember;

import java.util.ArrayList;

public class FamilyMemberAdapter extends RecyclerView.Adapter<FamilyMemberAdapter.FamilyMemberViewHolder> {

    Context mContext;
    ArrayList<FamilyMember> list;

    public FamilyMemberAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<FamilyMember> getList() {
        return list;
    }

    public void setList(ArrayList<FamilyMember> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FamilyMemberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_family_member, parent, false);
        return new FamilyMemberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FamilyMemberViewHolder holder, int position) {
        FamilyMember model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class FamilyMemberViewHolder extends RecyclerView.ViewHolder {

        CustomFamilyMemberBinding binding;

        private FamilyMemberViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomFamilyMemberBinding.bind(itemView);
        }

        private void bind(FamilyMember model) {
            binding.name.setText(Constants.setText(mContext, R.string.name, model.getName()));
            binding.birthday.setText(Constants.setText(mContext, R.string.birth, model.getBirthday()));
            binding.disability.setText(Constants.setText(mContext, R.string.disability,"\n"+ model.getDisability()));

//            binding.name.setText(model.getName());
//            binding.birthday.setText(model.getBirthday());
//            binding.disability.setText(model.getDisability());
        }
    }

}
