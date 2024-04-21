package com.horvia.horvia.ui.farms;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Product;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.adapter.ProductAdapter;

import java.util.ArrayList;

public class FarmDetailsActivity extends AppCompatActivity {

    private TextView farmName;
    private TabLayout tabLayout;
    private LinearLayout productList;


    private int farmId;
    private ArrayList<Category> categories = new ArrayList<>();

    private ApiRequest apiRequest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farm_details);

        farmId = getIntent().getIntExtra("id", -1);
        farmName = findViewById(R.id.farm_name);
        farmName.setText(getIntent().getStringExtra("name"));

        tabLayout = findViewById(R.id.tab_layout);
        productList = findViewById(R.id.product_list);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productList.removeAllViews();

                Log.d("changeTab", String.valueOf(tab.getPosition()));
                Category category = categories.get(tab.getPosition());
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), category.Products);

                for (int i = 0; i < productAdapter.getCount(); i++) {
                    View item = productAdapter.getView(i, null, null);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0,0,0,40);
                    item.setLayoutParams(layoutParams);

                    productList.addView(item);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        apiRequest = new ApiRequest(this);

        apiRequest.GetFarmCategories(farmId, new ApiRequestListener<ArrayList<Category>>() {
            @Override
            public void onComplete(@Nullable ArrayList<Category> entity, String error) {
                if(entity != null){
                    categories = entity;

                    for(Category category : entity){
                        tabLayout.addTab(tabLayout.newTab().setText(category.Name));
                    }
                }
            }
        });
    }

}