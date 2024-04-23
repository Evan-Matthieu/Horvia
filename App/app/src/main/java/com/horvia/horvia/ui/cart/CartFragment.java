package com.horvia.horvia.ui.cart;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Cart;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.farms.FarmDetailsActivity;
import com.horvia.horvia.ui.profile.OrdersFragment;
import com.horvia.horvia.ui.profile.PersonnalInformationsFragment;
import com.horvia.horvia.ui.profile.ProfileFragment;
import com.horvia.horvia.utils.adapter.CartAdapter;
import com.horvia.horvia.utils.adapter.ProductAdapter;

import java.util.ArrayList;

public class CartFragment extends Fragment {

    private ApiRequest apiRequest;
    private ImageView farmPicture;
    private TextView farmName;
    private LinearLayout productsList;
    private Button validateCart;

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        farmName = view.findViewById(R.id.farm_name);
        farmPicture = view.findViewById(R.id.farm_picture);
        validateCart = view.findViewById(R.id.validate_cart);

        productsList = view.findViewById(R.id.products_list);

        apiRequest = new ApiRequest(getContext());

        apiRequest.GetCartProducts(new ApiRequestListener<ArrayList<Cart>>() {
            @Override
            public void onComplete(@Nullable ArrayList<Cart> entity, String error) {
                if(entity != null){
                    farmName.setText(entity.get(0).Product.Farm.Name);
                    farmPicture.setImageBitmap(entity.get(0).Product.Farm.Picture);


                    CartAdapter cartAdapter = new CartAdapter(getContext(), entity);

                    for (int i = 0; i < cartAdapter.getCount(); i++) {
                        View contentView = cartAdapter.getView(i, null, null);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(0,0,0,40);
                        contentView.setLayoutParams(layoutParams);

                        ImageButton deleteCart = contentView.findViewById(R.id.delete_cart);

                        int cartId = Integer.parseInt(String.valueOf(cartAdapter.getItemId(i)));
                        deleteCart.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                apiRequest.DeleteProductToCart(cartId, new ApiRequestListener<String>() {
                                    @Override
                                    public void onComplete(@Nullable String entity, String error) {
                                        if(entity != null){
                                            Toast.makeText(getContext(), R.string.product_deleted, Toast.LENGTH_LONG).show();
                                            productsList.removeView(contentView);
                                        }
                                        else{
                                            Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });

                        productsList.addView(contentView);
                    }
                }
            }
        });

        validateCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiRequest.ValidateCart(new ApiRequestListener<String>() {
                    @Override
                    public void onComplete(@Nullable String entity, String error) {
                        if(entity != null){
                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.mainContentFragment, new OrdersFragment())
                                    .addToBackStack(null)
                                    .commit();
                            Toast.makeText(getContext(), R.string.validate_cart, Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });


        return view;
    }
}