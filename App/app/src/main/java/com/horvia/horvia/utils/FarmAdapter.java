package com.horvia.horvia.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Farm;

import java.util.ArrayList;

public class FarmAdapter extends BaseAdapter {

    ArrayList<Farm> farms;
    Context context;
    LayoutInflater inflater;

    public FarmAdapter(Context context, ArrayList<Farm> farms){

        this.context = context;
        this.farms = farms;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return farms.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.farms_list, null);
        ImageView imageView = view.findViewById(R.id.farm_list_img);
        TextView nameView = view.findViewById(R.id.farm_list_name);
        RatingBar ratingBar = view.findViewById(R.id.farm_list_rate);
        TextView ratingBarValue = view.findViewById(R.id.farm_rate_list_value);
        TextView ratingBarNumber = view.findViewById(R.id.farm_rate_list_number);

        imageView.setImageBitmap(farms.get(i).Picture);
        nameView.setText(farms.get(i).Name);
        if(farms.get(i).Rate == null){
            ratingBar.setVisibility(View.INVISIBLE);
            ratingBarValue.setVisibility(View.INVISIBLE);
            ratingBarNumber.setVisibility(View.INVISIBLE);
        }
        else{
            ratingBar.setRating(farms.get(i).Rate);
            ratingBarValue.setText(String.valueOf(farms.get(i).Rate));
            ratingBarNumber.setText("("+ farms.get(i).RateNumber+")");
        }

        return view;
    }
}
