package com.horvia.horvia.ui.farms;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ContentView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.MeasuringUnit;
import com.horvia.horvia.models.Product;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.adapter.ProductAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class FarmDetailsActivity extends AppCompatActivity {

    private TextView farmName;
    private TabLayout tabLayout;
    private LinearLayout productList;
    private PopupWindow window;


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

        window = new PopupWindow(getApplicationContext());


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                productList.removeAllViews();

                Category category = categories.get(tab.getPosition());
                ProductAdapter productAdapter = new ProductAdapter(getApplicationContext(), category.Products);

                for (int i = 0; i < productAdapter.getCount(); i++) {
                    View view = productAdapter.getView(i, null, null);

                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );
                    layoutParams.setMargins(0,0,0,40);
                    view.setLayoutParams(layoutParams);

                    Product product = productAdapter.getItem(i);
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            View contentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.popup_add_product_cart, null);
                            LinearLayout productLinearLayout = contentView.findViewById(R.id.product);

                            View productView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.product_list, null);
                            ImageView productPicture = productView.findViewById(R.id.product_picture);
                            TextView productName = productView.findViewById(R.id.product_name);
                            TextView productDescription = productView.findViewById(R.id.product_description);
                            TextView productUnitType = productView.findViewById(R.id.product_unit_type);
                            TextView productPrice = productView.findViewById(R.id.product_price);

                            productPicture.setImageBitmap(product.Picture);
                            productName.setText(product.Name);
                            productDescription.setText(product.Description);
                            productUnitType.setText("Prix au " + product.MeasuringUnit.getLabel(getApplicationContext()));
                            productPrice.setText(product.UnitPrice + "€");


                            productLinearLayout.addView(productView);

                            Button addButton = contentView.findViewById(R.id.add);
                            Button removeButton = contentView.findViewById(R.id.remove);
                            Button addToCart = contentView.findViewById(R.id.add_to_cart);
                            Button cancelButton = contentView.findViewById(R.id.cancel);
                            TextView quantity = contentView.findViewById(R.id.quantity);
                            TextView totalPrice = contentView.findViewById(R.id.total_price);

                            if(product.MeasuringUnit == MeasuringUnit.WEIGHT){
                                addButton.setText("+ 100g");
                                removeButton.setText("- 100g");
                                quantity.setText("0 g");
                            }
                            else if(product.MeasuringUnit == MeasuringUnit.LITRE){
                                addButton.setText("+ 0.5l");
                                removeButton.setText("- 0.5l");
                                quantity.setText("0 l");
                            }
                            else{
                                addButton.setText("+ 1");
                                removeButton.setText("- 1");
                                quantity.setText("0");
                            }

                            addButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DecimalFormat dc = new DecimalFormat("#.##");
                                    dc.setRoundingMode(RoundingMode.UP);

                                    double actualQuantity = Double.parseDouble(quantity.getText().toString().split(" ")[0]);

                                    if(product.MeasuringUnit == MeasuringUnit.WEIGHT && actualQuantity < 1000){
                                        actualQuantity += 100;
                                        quantity.setText(actualQuantity + " g");

                                        String totalPriceValue = dc.format((double) actualQuantity / 1000 * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                    else if (product.MeasuringUnit == MeasuringUnit.LITRE && actualQuantity < 10){
                                        actualQuantity += 0.5;
                                        quantity.setText(actualQuantity + " l");

                                        String totalPriceValue = dc.format(actualQuantity * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                    else if(product.MeasuringUnit == MeasuringUnit.NUMBER && actualQuantity < 50){
                                        actualQuantity += 1;
                                        quantity.setText(String.valueOf(Math.round(actualQuantity)));

                                        String totalPriceValue = dc.format(actualQuantity * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                }
                            });

                            removeButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    DecimalFormat dc = new DecimalFormat("#.##");
                                    dc.setRoundingMode(RoundingMode.UP);

                                    double actualQuantity = Double.parseDouble(quantity.getText().toString().split(" ")[0]);

                                    if(product.MeasuringUnit == MeasuringUnit.WEIGHT && actualQuantity > 0){
                                        actualQuantity -= 100;
                                        quantity.setText(actualQuantity + " g");

                                        String totalPriceValue = dc.format(actualQuantity / 100000 * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                    else if (product.MeasuringUnit == MeasuringUnit.LITRE && actualQuantity > 0){
                                        actualQuantity -= 0.5;
                                        quantity.setText(actualQuantity + " l");

                                        String totalPriceValue = dc.format(actualQuantity * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                    else if(product.MeasuringUnit == MeasuringUnit.NUMBER && actualQuantity > 0){
                                        actualQuantity -= 1;
                                        quantity.setText(String.valueOf(Math.round(actualQuantity)));

                                        String totalPriceValue = dc.format(actualQuantity * product.UnitPrice);
                                        totalPrice.setText(totalPriceValue + "€");
                                    }
                                }
                            });

                            addToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    double actualQuantity = Double.parseDouble(quantity.getText().toString().split(" ")[0]);
                                    if(actualQuantity > 0){
                                        apiRequest.AddProductToCart(product.Id, actualQuantity, new ApiRequestListener<String>() {
                                            @Override
                                            public void onComplete(@Nullable String entity, String error) {
                                                if(entity != null){
                                                    Toast.makeText(getApplicationContext(), R.string.product_add_to_cart, Toast.LENGTH_LONG).show();
                                                    window.dismiss();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            cancelButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    window.dismiss();
                                }
                            });

                            window.setContentView(contentView);
                            window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                            window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

                            window.showAtLocation(view, Gravity.CENTER, 0, 0);
                        }
                    });


                    productList.addView(view);
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