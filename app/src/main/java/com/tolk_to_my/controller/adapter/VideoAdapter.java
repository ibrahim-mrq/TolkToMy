package com.tolk_to_my.controller.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.tolk_to_my.R;
import com.tolk_to_my.controller.activities.VideoActivity;
import com.tolk_to_my.databinding.CustomVideoBinding;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.Video;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    Context mContext;
    ArrayList<Video> list;

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public ArrayList<Video> getList() {
        return list;
    }

    public void setList(ArrayList<Video> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_video, parent, false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        Video model = list.get(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return (list != null ? list.size() : 0);
    }

    class VideoViewHolder extends RecyclerView.ViewHolder {

        CustomVideoBinding binding;

        private VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = CustomVideoBinding.bind(itemView);
        }

        private void bind(Video model) {
            binding.title.setText(model.getTitle());

            String videoId = model.getUrl().substring(model.getUrl().indexOf("=") + 1);
            Picasso.get().load("https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg")
                    .into(binding.imageView);

            itemView.setOnClickListener(view -> {
                mContext.startActivity(new Intent(mContext, VideoActivity.class)
                        .putExtra(Constants.TYPE_MODEL, model)
                        .putExtra(Constants.TYPE_ID, videoId)
                );
            });

        }
    }

}
