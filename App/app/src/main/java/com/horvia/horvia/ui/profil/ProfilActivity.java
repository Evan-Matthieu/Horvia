package com.horvia.horvia.ui.profil;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.horvia.horvia.R;
import com.horvia.horvia.databinding.ActivityProfilBinding;

public class ProfilActivity extends AppCompatActivity {

    private ActivityProfilBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}