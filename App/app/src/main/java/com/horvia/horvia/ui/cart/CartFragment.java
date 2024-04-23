package com.horvia.horvia.ui.cart;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.profile.OrdersFragment;
import com.horvia.horvia.ui.profile.ProfileFragment;
import com.horvia.horvia.utils.adapter.CartAdapter;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartFragment extends Fragment {

    private ApiRequest apiRequest;
    private TextView cartEmpty;
    private LinearLayout orderDetails;
    private Button validateCart;

    ArrayList<Cart> carts = new ArrayList<>();

    public CartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        cartEmpty = view.findViewById(R.id.cart_empty);
        orderDetails = view.findViewById(R.id.order_details);
        validateCart = view.findViewById(R.id.validate_cart);

        apiRequest = new ApiRequest(getContext());

        apiRequest.GetCartProducts(new ApiRequestListener<ArrayList<Cart>>() {
            @Override
            public void onComplete(@Nullable ArrayList<Cart> entity, String error) {
                if(entity != null && entity.size() == 0){
                    cartEmpty.setVisibility(View.VISIBLE);
                }
                else if(entity != null && entity.size() > 0){
                    orderDetails.setVisibility(View.VISIBLE);

                    LinearLayout orderDetailsLayout = view.findViewById(R.id.order_details);

                    View orderDetailsView = inflater.inflate(R.layout.order_details, null);
                    orderDetailsLayout.addView(orderDetailsView);

                    TextView farmName = orderDetailsView.findViewById(R.id.farm_name);
                    ImageView farmPicture = orderDetailsView.findViewById(R.id.farm_picture);
                    LinearLayout productsList = orderDetailsView.findViewById(R.id.products_list);
                    TextView totalPrice = orderDetailsView.findViewById(R.id.total_price);



                    double cartTotalPriceValue = 0.00;

                    carts = entity;
                    orderDetails.setVisibility(View.VISIBLE);

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
                                            productsList.removeView(contentView);
                                            carts.removeIf(cart -> cart.Id == cartId);

                                            if(carts.size() == 0){
                                                orderDetails.setVisibility(View.GONE);
                                                cartEmpty.setVisibility(View.VISIBLE);
                                            }
                                        }
                                        else{
                                            Toast.makeText(getContext(), R.string.error_occured, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });

                        productsList.addView(contentView);
                        cartTotalPriceValue += cartAdapter.getItem(i).Quantity * cartAdapter.getItem(i).Product.UnitPrice;

                    }
                    DecimalFormat dc = new DecimalFormat("#.##");
                    dc.setRoundingMode(RoundingMode.UP);

                    totalPrice.setText("Total : " + dc.format(cartTotalPriceValue) + "€");
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
                                    .addToBackStack(ProfileFragment.class.toString())
                                    .commit();
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