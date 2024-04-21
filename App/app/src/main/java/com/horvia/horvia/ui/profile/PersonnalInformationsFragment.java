package com.horvia.horvia.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Civility;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

public class PersonnalInformationsFragment extends Fragment {

    private ApiRequest apiRequest;
    private TextView firstname, lastname, email, phoneNumber, birthDate, password, modifyPassword;
    private RadioButton civilityMadam, civilityMister;
    private ImageView back;
    private Button submitPersonnalInformations;

    private Civility civility;
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
        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        birthDate = view.findViewById(R.id.birth_date);

        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phoneNumber = view.findViewById(R.id.phone_number);

        modifyPassword = view.findViewById(R.id.modify_password);
        submitPersonnalInformations = view.findViewById(R.id.submit_personnal_informations);
        back = view.findViewById(R.id.back);

        civilityMadam = view.findViewById(R.id.civility_madam);
        civilityMister = view.findViewById(R.id.civility_mister);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        apiRequest = new ApiRequest(getContext());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.mainContentFragment, new ModifyPasswordFragment())
                        .addToBackStack(PersonnalInformationsFragment.class.toString())
                        .commit();


            }
        });

        submitPersonnalInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firstname.length() == 0){
                    firstname.setError(getResources().getString(R.string.required_field));
                }
                else if(lastname.length() == 0){
                    lastname.setError(getResources().getString(R.string.required_field));
                }
                else if(birthDate.length() == 0){
                    birthDate.setError(getResources().getString(R.string.required_field));
                }
                else if(email.length() == 0){
                    email.setError(getResources().getString(R.string.required_field));
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()){
                    email.setError(getResources().getString(R.string.invalid_email));
                }
                else if(phoneNumber.length() == 0){
                    phoneNumber.setError(getResources().getString(R.string.required_field));
                }
                else if(!Pattern.compile("^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$").matcher(phoneNumber.getText().toString()).matches()){
                    phoneNumber.setError(getResources().getString(R.string.invalid_phone_number));
                }

                User user = new User();
                user.FirstName = firstname.getText().toString();
                user.Lastname = lastname.getText().toString();
                user.Civility = civilityMadam.isChecked() ? Civility.MADAM : Civility.MISTER;
                try {
                    user.BirthDate = dateFormat.parse(birthDate.getText().toString());
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                user.Email = email.getText().toString();
                user.PhoneNumber = phoneNumber.getText().toString();

                apiRequest.UpdateUserPersonnalInformations(user, new ApiRequestListener<String>() {
                    @Override
                    public void onComplete(@Nullable String entity, String error) {
                        if(entity != null){
                            SharedPreferences sharedPreferences = getContext().getSharedPreferences("User_Login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("jwtToken", entity);
                            editor.apply();

                            Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getContext(), R.string.informations_updated, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        apiRequest.GetUsers(new ApiRequestListener<User>() {

            @Override
            public void onComplete(@Nullable User entity, String error) {
                if(entity != null) {
                    firstname.setText(entity.FirstName);
                    lastname.setText(entity.Lastname);
                    if(entity.Civility == Civility.MADAM){
                        civilityMadam.setChecked(true);
                    }
                    else{
                        civilityMister.setChecked(true);
                    }

                    String birthDateString = dateFormat.format(entity.BirthDate);
                    birthDate.setText(birthDateString);

                    email.setText(entity.Email);
                    password.setText("vqsd$:dqsds45");
                    phoneNumber.setText(entity.PhoneNumber);



                }
            }
        });
        return view;
    }
}