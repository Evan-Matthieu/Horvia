package com.horvia.horvia.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.farms.FarmDetailsActivity;
import com.horvia.horvia.ui.search.SearchFragment;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.adapter.MainCategoryAdapter;
import com.horvia.horvia.utils.pagination.PaginationParams;
import com.horvia.horvia.utils.pagination.PaginationResult;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    LinearLayout llFarmList, llMainCategories;
    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        ApiRequest apiRequest = new ApiRequest(getContext());

        llFarmList = rootView.findViewById(R.id.farm_list);
        llMainCategories = rootView.findViewById(R.id.main_categories_list);

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
                            SearchFragment fragment = new SearchFragment();
                            Bundle args = new Bundle();
                            args.putInt("categoryId", category.Id);
                            fragment.setArguments(args);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.mainContentFragment, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        });
                    }
                }

            }
        });

        apiRequest.GetFarms(new PaginationParams(),null,  new ApiRequestListener<PaginationResult<Farm>>() {
            @Override
            public void onComplete(@Nullable PaginationResult<Farm> entity, String error) {
                if(entity != null){
                    FarmAdapter farmAdapter = new FarmAdapter(container.getContext(), entity.Items);

                    for (int i = 0; i < farmAdapter.getCount(); i++) {
                        Log.d("description", farmAdapter.getItem(i).Description);
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
                    Toast.makeText(container.getContext(),error, Toast.LENGTH_LONG).show();
                }
            }
        });
        return rootView;
    }
}