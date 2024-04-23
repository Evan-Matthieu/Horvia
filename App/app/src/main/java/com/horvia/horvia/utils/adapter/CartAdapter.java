package com.horvia.horvia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Cart;
import com.horvia.horvia.models.MeasuringUnit;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Cart> carts;
    private LayoutInflater inflater;

    public CartAdapter(Context context, ArrayList<Cart> carts){
        this.context = context;
        this.carts = carts;
        this.inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return carts.size();
    }

    @Override
    public Object getItem(int i) {
        return carts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return carts.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.cart_list, null);
        ImageView productPicture = view.findViewById(R.id.product_picture);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productQuantity = view.findViewById(R.id.product_quantity);
        TextView productPrice = view.findViewById(R.id.product_price);

        productPicture.setImageBitmap(carts.get(i).Product.Picture);
        productName.setText(carts.get(i).Product.Name);

        DecimalFormat dc = new DecimalFormat("#.##");
        dc.setRoundingMode(RoundingMode.UP);


        if(carts.get(i).Product.MeasuringUnit == MeasuringUnit.LITRE){
            productQuantity.setText("Quantité : " + carts.get(i).Quantity + "l");
            productPrice.setText(dc.format((carts.get(i).Product.UnitPrice * carts.get(i).Quantity)) + "€");
        }
        else if(carts.get(i).Product.MeasuringUnit == MeasuringUnit.WEIGHT){
            if(carts.get(i).Quantity >= 1000){
                DecimalFormat dcWeight = new DecimalFormat("#.#");
                productQuantity.setText("Quantité : " + (dcWeight.format((carts.get(i).Quantity) / 1000)) + "kg");
            }else{
                productQuantity.setText("Quantité : " + Math.round(carts.get(i).Quantity) + "g");
            }
            productPrice.setText(dc.format((carts.get(i).Product.UnitPrice * carts.get(i).Quantity / 1000)) + "€");
        }
        else{
            productQuantity.setText("Quantité : " + Math.round(carts.get(i).Quantity));
            productPrice.setText(dc.format((carts.get(i).Product.UnitPrice * carts.get(i).Quantity)) + "€");

        }

        return view;
    }
}
