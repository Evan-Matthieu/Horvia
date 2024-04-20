package com.horvia.horvia.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.horvia.horvia.R;
import com.horvia.horvia.ui.login.LoginActivity;

public class ProfileFragment extends Fragment {

    LinearLayout personnalInformationsLayout, addressLayout, lastOrdersLayout, logoutLoayout;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        personnalInformationsLayout = view.findViewById(R.id.personnal_informations_layout);
        addressLayout = view.findViewById(R.id.address_layout);
        lastOrdersLayout = view.findViewById(R.id.last_order_layout);
        logoutLoayout = view.findViewById(R.id.logout_layout);

        personnalInformationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new PersonnalInformationsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new AddressFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        lastOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new LastOrdersFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        logoutLoayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
                Editor editor = sharedPreferences.edit();
                editor.remove("jwtToken");
                editor.apply();

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}