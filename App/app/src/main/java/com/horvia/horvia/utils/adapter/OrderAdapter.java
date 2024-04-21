package com.horvia.horvia.utils.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Order;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {

    ArrayList<Order> orders;
    Context context;
    LayoutInflater inflater;

    public OrderAdapter(Context context, ArrayList<Order> orders){

        this.context = context;
        this.orders = orders;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Order getItem(int i) {
        return orders.get(i);
    }

    @Override
    public long getItemId(int i) {
        return orders.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.orders_list, null);
        ImageView farmPicture = view.findViewById(R.id.order_picture);
        TextView orderId = view.findViewById(R.id.order_id);
        TextView status = view.findViewById(R.id.order_status);
        TextView farmName = view.findViewById(R.id.order_farm_name);
        TextView totalPrice = view.findViewById(R.id.order_total_price);

        farmPicture.setImageBitmap(orders.get(i).Farm.Picture);
        orderId.setText("N° " + orders.get(i).Id);
        status.setText("Status : " + orders.get(i).Status.getLabel(context));
        farmName.setText(orders.get(i).Farm.Name);
        totalPrice.setText(orders.get(i).TotalPrice + "€");

        return view;
    }
}
