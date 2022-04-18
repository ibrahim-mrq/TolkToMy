package com.tolk_to_my.controller.activities;

import androidx.annotation.NonNull;

import android.os.Bundle;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.tolk_to_my.databinding.ActivityVideoBinding;
import com.tolk_to_my.helpers.BaseActivity;
import com.tolk_to_my.helpers.Constants;
import com.tolk_to_my.model.Video;

public class VideoActivity extends BaseActivity {

    ActivityVideoBinding binding;
    Video model;
    String videoId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityVideoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        model = (Video) getIntent().getSerializableExtra(Constants.TYPE_MODEL);
        videoId = getIntent().getStringExtra(Constants.TYPE_ID);

        binding.appbar.tvTool.setText(model.getTitle());
        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());

        getLifecycle().addObserver(binding.youtubePlayerView);
        binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                youTubePlayer.loadVideo(videoId, 0);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}