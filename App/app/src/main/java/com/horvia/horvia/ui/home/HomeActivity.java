package com.horvia.horvia.ui.home;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.horvia.horvia.R;
import com.horvia.horvia.databinding.ActivityCartBinding;
import com.horvia.horvia.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}