package com.tolk_to_my.controller.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.barteksc.pdfviewer.util.FitPolicy;
import com.tolk_to_my.R;
import com.tolk_to_my.databinding.ActivityPdfBinding;

public class PdfActivity extends AppCompatActivity {

    ActivityPdfBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPdfBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.appbar.imgBack.setOnClickListener(view -> onBackPressed());
        binding.appbar.tvTool.setText(getString(R.string.about));

        binding.pdfView.fromAsset("take_to_my.pdf")

                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .pageFitPolicy(FitPolicy.BOTH)
                .fitEachPage(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)

                .load();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}