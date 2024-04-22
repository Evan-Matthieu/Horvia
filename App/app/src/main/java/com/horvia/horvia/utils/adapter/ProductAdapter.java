package com.horvia.horvia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Product;

import java.util.ArrayList;

public class ProductAdapter extends BaseAdapter {

    ArrayList<Product> products;
    Context context;
    LayoutInflater inflater;

    public ProductAdapter(Context context, ArrayList<Product> products){
        this.context = context;
        this.products = products;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int i) {
        return products.get(i);
    }

    @Override
    public long getItemId(int i) {
        return products.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.product_list, null);
        ImageView productPicture = view.findViewById(R.id.product_picture);
        TextView productName = view.findViewById(R.id.product_name);
        TextView productUnitType = view.findViewById(R.id.product_unit_type);
        TextView productPrice = view.findViewById(R.id.product_price);

        productPicture.setImageBitmap(products.get(i).Picture);
        productName.setText(products.get(i).Name);
        productUnitType.setText(products.get(i).MeasuringUnit.getLabel(context));
        productPrice.setText(products.get(i).UnitPrice + "€");


        return view;
    }
}
