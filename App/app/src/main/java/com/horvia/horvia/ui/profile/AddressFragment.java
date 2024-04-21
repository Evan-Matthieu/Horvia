package com.horvia.horvia.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.login.LoginActivity;

import java.util.Calendar;

public class AddressFragment extends Fragment {
    private ApiRequest apiRequest;
    private TextView address, city, zipCode, furtherDetails;

    public AddressFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_address, container, false);

        address = view.findViewById(R.id.address_address);
        city = view.findViewById(R.id.address_city);
        zipCode = view.findViewById(R.id.address_zipcode);
        furtherDetails = view.findViewById(R.id.address_details);

        apiRequest = new ApiRequest(getContext());
        apiRequest.GetAddresses(new ApiRequestListener<Location>() {

            @Override
            public void onComplete(@Nullable Location entity, String error) {
                if(entity != null) {
                    address.setText(entity.Address);
                    city.setText(entity.City);
                    zipCode.setText(entity.ZipCode);
                    furtherDetails.setText(entity.FurtherDetails);
                }
            }
        });
        return view;
    }
}
