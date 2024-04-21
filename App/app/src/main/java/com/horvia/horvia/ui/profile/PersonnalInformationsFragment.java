package com.horvia.horvia.ui.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.horvia.horvia.R;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PersonnalInformationsFragment extends Fragment {

    private ApiRequest apiRequest;
    private TextView firstname, lastname, email, phone, birth, civility;
    public PersonnalInformationsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_personnal_informations, container, false);
        firstname = view.findViewById(R.id.personnal_firstname);
        lastname = view.findViewById(R.id.personnal_lastname);
        email = view.findViewById(R.id.personnal_email);
        phone = view.findViewById(R.id.personnal_phone);
        birth = view.findViewById(R.id.personnal_birth);
        civility = view.findViewById(R.id.personnal_civility);


        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        apiRequest = new ApiRequest(getContext());

        apiRequest.GetUsers(new ApiRequestListener<User>() {

            @Override
            public void onComplete(@Nullable User entity, String error) {
                if(entity != null) {
                    firstname.setText(entity.FirstName);
                    lastname.setText(entity.Lastname);
                    email.setText(entity.Email);
                    phone.setText(entity.PhoneNumber);

                    String birthDateString = dateFormat.format(entity.BirthDate);
                    birth.setText(birthDateString);

                    civility.setText(entity.Civility.getLabel());

                }
            }
        });
        return view;
    }
}