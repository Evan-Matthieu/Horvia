package com.horvia.horvia.ui.farms;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;


public class FarmDetailFragment extends Fragment {

    private int FarmId;
    private String FarmName;
    private ApiRequest apiRequest;

    private TextView tvName;


    public FarmDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            FarmId = getArguments().getInt("id");
            FarmName = getArguments().getString("name");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_farm_detail, container, false);

        tvName = rootView.findViewById(R.id.farm_name);


        apiRequest = new ApiRequest(rootView.getContext());
        tvName.setText(FarmName);

        return rootView;
    }
}