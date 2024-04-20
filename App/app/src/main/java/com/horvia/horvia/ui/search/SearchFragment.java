package com.horvia.horvia.ui.search;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.farms.FarmDetailFragment;
import com.horvia.horvia.utils.adapter.FarmAdapter;
import com.horvia.horvia.utils.pagination.PaginationParams;
import com.horvia.horvia.utils.pagination.PaginationResult;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ArrayList<Integer> categoriesSelectedId = new ArrayList<>();
    private ApiRequest apiRequest;

    private LinearLayout llFarmList;
    private ImageButton searchButton, filterButton;
    private EditText queryEditText;
    private Button seeMoreButton;
    private TextView noFarmFoundTextView;

    private String currentQuery = null;
    private int currentPageSize = 3;
    private int currentPageNumber = 1;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().get("categoryId") != null){
                categoriesSelectedId.add(getArguments().getInt("categoryId"));
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
        filterButton = view.findViewById(R.id.filter_button);
        queryEditText = view.findViewById(R.id.search_query);
        seeMoreButton = view.findViewById(R.id.search_see_more);
        noFarmFoundTextView = view.findViewById(R.id.no_farm_found);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQuery = queryEditText.getText().toString();
                Log.d("currentQuery", currentQuery);
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


        fetchFarmPaginated();
        return view;
    }

    private void fetchFarmPaginated(){
        PaginationParams params = new PaginationParams(currentPageSize, currentPageNumber, currentQuery);
        apiRequest.GetFarms(params,categoriesSelectedId,  new ApiRequestListener<PaginationResult<Farm>>() {
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
                            FarmDetailFragment fragment = new FarmDetailFragment();
                            Bundle args = new Bundle();
                            args.putInt("id", farm.Id);
                            args.putString("name", farm.Name);
                            fragment.setArguments(args);

                            getParentFragmentManager().beginTransaction()
                                    .replace(R.id.mainContentFragment, fragment)
                                    .addToBackStack(null)
                                    .commit();
                        });
                    }
                }
                else{
                    Toast.makeText(getContext(),error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}