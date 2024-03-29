package com.horvia.horvia.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.horvia.horvia.R;
import com.horvia.horvia.databinding.ActivitySearchBinding;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}