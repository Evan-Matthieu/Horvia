package com.horvia.horvia.utils.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainCategoryAdapter extends BaseAdapter {

    ArrayList<Category> categories;
    Context context;
    LayoutInflater inflater;

    public MainCategoryAdapter(Context context, ArrayList<Category> categories){

        this.context = context;
        this.categories = categories;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    @Override
    public Category getItem(int i) {
        return categories.get(i);
    }

    @Override
    public long getItemId(int i) {
        return categories.get(i).Id;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.main_categories_list, null);
        ImageView imageView = view.findViewById(R.id.main_category_img);
        TextView nameView = view.findViewById(R.id.main_category_name);
        TextView countView = view.findViewById(R.id.main_category_count);

        Log.d("categoryN", String.valueOf(categories.get(i).FarmNumber));


        imageView.setImageBitmap(categories.get(i).Picture);
        nameView.setText(categories.get(i).Name);
        countView.setText("("+ categories.get(i).FarmNumber +")");

        return view;
    }
}
