package com.horvia.horvia.ui.search;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.farms.FarmDetailsActivity;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.adapter.MainCategoryAdapter;
import com.horvia.horvia.utils.pagination.PaginationParams;
import com.horvia.horvia.utils.pagination.PaginationResult;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    @Nullable
    private Integer categoryId = null;
    private ApiRequest apiRequest;

    private LinearLayout llFarmList, llMainCategories;
    private ImageButton searchButton;
    private EditText queryEditText;
    private Button seeMoreButton;
    private TextView noFarmFoundTextView;

    private String currentQuery = "";
    private int currentPageSize = 10;
    private int currentPageNumber = 1;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("categoryIdArguments",String.valueOf(getArguments().getInt("categoryId")));
        if (getArguments() != null) {
            if(getArguments().get("categoryId") != null){
                categoryId = getArguments().getInt("categoryId");
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        apiRequest = new ApiRequest(getContext());

        llFarmList = view.findViewById(R.id.farm_list);
        searchButton = view.findViewById(R.id.search_button);
        queryEditText = view.findViewById(R.id.search_query);
        seeMoreButton = view.findViewById(R.id.search_see_more);
        noFarmFoundTextView = view.findViewById(R.id.no_farm_found);

        llMainCategories = view.findViewById(R.id.main_categories_list);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuery = queryEditText.getText().toString();
                llFarmList.removeAllViews();
                fetchFarmPaginated();
            }
        });

        seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentPageNumber = currentPageNumber + 1;
                fetchFarmPaginated();
            }
        });

        apiRequest.GetMainCategories(new ApiRequestListener<ArrayList<Category>>() {
            @Override
            public void onComplete(@Nullable ArrayList<Category> entity, String error) {
                if(entity != null){
                    MainCategoryAdapter categoryAdapter = new MainCategoryAdapter(container.getContext(), entity);


                    for (int i = 0; i < categoryAdapter.getCount(); i++) {
                        View item = categoryAdapter.getView(i, null, null);
                        item.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1));
                        llMainCategories.addView(item);

                        Category category = categoryAdapter.getItem(i);

                        item.setOnClickListener(view -> {
                            if(categoryId != null && categoryId == category.Id){
                                categoryId = null;
                            }
                            else{
                                categoryId = category.Id;
                            }

                            changeCategorySelected(category.Id, categoryAdapter);
                            llFarmList.removeAllViews();
                            fetchFarmPaginated();
                        });
                    }
                    if(categoryId != null){
                        changeCategorySelected(categoryId, categoryAdapter);
                    }
                }

            }
        });


        fetchFarmPaginated();
        return view;
    }

    private void fetchFarmPaginated(){
        Log.d("currentQuery", currentQuery);
        PaginationParams params = new PaginationParams(currentPageSize, currentPageNumber, currentQuery.isEmpty() ? null : currentQuery);
        apiRequest.GetFarms(params ,categoryId,  new ApiRequestListener<PaginationResult<Farm>>() {
            @Override
            public void onComplete(@Nullable PaginationResult<Farm> entity, String error) {
                if(entity != null){
                    if(entity.TotalCount == 0){
                        noFarmFoundTextView.setVisibility(View.VISIBLE);
                        seeMoreButton.setVisibility(View.GONE);
                    }
                    else if(currentPageSize * currentPageNumber > entity.TotalCount){
                        noFarmFoundTextView.setVisibility(View.GONE);
                        seeMoreButton.setVisibility(View.GONE);
                    }
                    else{
                        noFarmFoundTextView.setVisibility(View.GONE);
                        seeMoreButton.setVisibility(View.VISIBLE);
                    }

                    FarmAdapter farmAdapter = new FarmAdapter(getContext(), entity.Items);

                    for (int i = 0; i < farmAdapter.getCount(); i++) {
                        View item = farmAdapter.getView(i, null, null);

                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(0,0,0,40);
                        item.setLayoutParams(layoutParams);

                        llFarmList.addView(item);
                        Farm farm = farmAdapter.getItem(i);

                        item.setOnClickListener(view -> {
                            Intent intent = new Intent(getActivity(), FarmDetailsActivity.class);
                            intent.putExtra("id", farm.Id);
                            intent.putExtra("name", farm.Name);
                            startActivity(intent);
                        });
                    }
                }
                else{
                    Toast.makeText(getContext(),error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void changeCategorySelected(int categoryIdSelected, MainCategoryAdapter adapter){
        int childCount = llMainCategories.getChildCount();

        for (int i = 0; i< childCount; i++){
            View childView = llMainCategories.getChildAt(i);

            TextView categoryName = childView.findViewById(R.id.main_category_name);
            if(categoryId != null && categoryId == adapter.getItem(i).Id){
                categoryName.setTextColor(getResources().getColor(R.color.white));
                childView.setBackgroundResource(R.drawable.rounded_bg_secondary);
            }
            else{
                categoryName.setTextColor(getResources().getColor(R.color.black));
                childView.setBackground(null);
            }
        }
    }
}