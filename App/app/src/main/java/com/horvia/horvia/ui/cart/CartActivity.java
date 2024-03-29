package com.horvia.horvia.ui.cart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.horvia.horvia.R;
import com.horvia.horvia.databinding.ActivityCartBinding;
import com.horvia.horvia.databinding.ActivityHomeBinding;

public class CartActivity extends AppCompatActivity {

    private ActivityCartBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}