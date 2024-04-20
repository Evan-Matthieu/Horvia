package com.horvia.horvia.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;
import com.horvia.horvia.ui.login.LoginActivity;

import java.util.Calendar;

public class ProfileFragment extends Fragment {

    private LinearLayout personnalInformationsLayout, addressLayout, lastOrdersLayout, logoutLoayout;
    private TextView age, name;
    private ImageView picture;
    private ApiRequest apiRequest;

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
        name = view.findViewById(R.id.user_name);
        picture = view.findViewById(R.id.user_picture);
        age = view.findViewById(R.id.user_age);

        apiRequest = new ApiRequest(getContext());

        personnalInformationsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new PersonnalInformationsFragment())
                        .addToBackStack(ProfileFragment.class.toString())
                        .commit();
            }
        });

        addressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new AddressFragment())
                        .addToBackStack(ProfileFragment.class.toString())
                        .commit();
            }
        });

        lastOrdersLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new LastOrdersFragment())
                        .addToBackStack(ProfileFragment.class.toString())
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

        apiRequest.GetUsers(new ApiRequestListener<User>() {

            @Override
            public void onComplete(@Nullable User entity, String error) {
                if(entity != null) {
                    name.setText(entity.FirstName +" " + entity.Lastname);
                    picture.setImageBitmap(entity.Picture);

                    Calendar birthDate = Calendar.getInstance();
                    birthDate.setTime(entity.BirthDate);

                    Calendar toDay = Calendar.getInstance();

                    int userAge = toDay.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
                    if (toDay.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
                        userAge--;
                    }
                    age.setText(userAge +" "+ getResources().getString(R.string.year));
                }
            }
        });
        return view;
    }
}