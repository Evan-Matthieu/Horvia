package com.horvia.horvia;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.horvia.horvia.databinding.ActivityMainBinding;
import com.horvia.horvia.ui.cart.CartFragment;
import com.horvia.horvia.ui.home.HomeFragment;
import com.horvia.horvia.ui.profile.ProfileFragment;
import com.horvia.horvia.ui.search.SearchFragment;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView bottomNavigationView = binding.bottomNavigationView;
        UppercaseMenuText(bottomNavigationView.getMenu());

        binding.bottomNavigationView.setBackground(null);
        binding.bottomNavigationView.setOnItemSelectedListener(this::onItemSelected);
        binding.bottomNavigationView.setSelectedItemId(R.id.home_nav);
    }

    private boolean onItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
            if(id == R.id.home_nav){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContentFragment, new HomeFragment())
                        .commit();
                return true;
            }

            else if(id == R.id.search_nav){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContentFragment, new SearchFragment())
                        .commit();
                return true;
            }

            else if(id == R.id.cart_nav){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContentFragment, new CartFragment())
                        .commit();
                return true;
            }

            else if(id == R.id.profile_nav){
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.mainContentFragment, new ProfileFragment())
                        .commit();
                return true;
            }
            return false;
    }

    private void UppercaseMenuText(@NonNull Menu menu){
        for (int i = 0; i < menu.size(); i++) {
            MenuItem menuItem = menu.getItem(i);
            String title = menuItem.getTitle().toString();
            menuItem.setTitle(title.substring(0, 1).toUpperCase() + title.substring(1));
        }
    }
}