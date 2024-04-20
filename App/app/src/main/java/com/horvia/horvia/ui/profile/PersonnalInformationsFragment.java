package com.horvia.horvia.ui.profile;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.horvia.horvia.R;
import com.horvia.horvia.models.User;
import com.horvia.horvia.network.ApiRequest;
import com.horvia.horvia.network.ApiRequestListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

public class PersonnalInformationsFragment extends Fragment {

    private EditText firstname, lastname, civility, birthDate, email, password, phoneNumber;
    private TextView modifyPassword;
    private Button submitButton;
    private ImageView back;

    private ApiRequest apiRequest;

    public PersonnalInformationsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_personnal_informations, container, false);

        firstname = view.findViewById(R.id.firstname);
        lastname = view.findViewById(R.id.lastname);
        civility = view.findViewById(R.id.civility);
        birthDate = view.findViewById(R.id.birth_date);
        email = view.findViewById(R.id.email);
        password = view.findViewById(R.id.password);
        phoneNumber = view.findViewById(R.id.phone_number);
        modifyPassword = view.findViewById(R.id.modify_password);
        submitButton = view.findViewById(R.id.submit_personnal_informations);
        back = view.findViewById(R.id.back);

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

        submitButton.setOnClickListener(new View.OnClickListener() {
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
                else{
                    // TODO faire la requête pour la modification du user
                }
            }
        });

        apiRequest.GetUsers(new ApiRequestListener<User>() {
            @Override
            public void onComplete(@Nullable User entity, String error) {
                if(entity != null){
                    firstname.setText(entity.FirstName);
                    lastname.setText(entity.Lastname);
                    civility.setText(entity.Civility.getLabel());

                    SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyy", Locale.getDefault());
                    birthDate.setText(formatter.format(entity.BirthDate));
                    email.setText(entity.Email);
                    password.setText("bdhqsvjqhkvdqsdb");
                    phoneNumber.setText(entity.PhoneNumber);


                    //picture.setImageBitmap(entity.Picture);
                }
            }
        });

        return view;
    }
}