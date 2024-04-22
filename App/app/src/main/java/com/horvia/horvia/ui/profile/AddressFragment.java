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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.login.LoginActivity;

import java.util.Calendar;

public class AddressFragment extends Fragment {
    private ApiRequest apiRequest;
    private EditText address, city, zipCode, furtherDetails;
    private Button submitButton;

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

        address = view.findViewById(R.id.street);
        city = view.findViewById(R.id.city);
        zipCode = view.findViewById(R.id.zip_code);
        furtherDetails = view.findViewById(R.id.further_details);
        submitButton = view.findViewById(R.id.submit_address);

        apiRequest = new ApiRequest(getContext());

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(address.length() == 0){
                    address.setError(getResources().getString(R.string.required_field));
                }
                else if(city.length() == 0){
                    city.setError(getResources().getString(R.string.required_field));
                }
                else if(zipCode.length() == 0){
                    zipCode.setError(getResources().getString(R.string.required_field));
                }
                else if(zipCode.length() != 5){
                    zipCode.setError(getResources().getString(R.string.zip_code_must_be_5));
                }
                else {
                    Location location = new Location();
                    location.Address = address.getText().toString();
                    location.City = city.getText().toString();
                    location.ZipCode = zipCode.getText().toString();
                    location.FurtherDetails = furtherDetails.getText().toString();

                    apiRequest.UpdateUserAddress(location, new ApiRequestListener<String>() {
                        @Override
                        public void onComplete(@Nullable String entity, String error) {
                            if(entity != null){
                                Toast.makeText(getContext(), R.string.address_modified, Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

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
