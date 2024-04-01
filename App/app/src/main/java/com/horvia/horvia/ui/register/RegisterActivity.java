package com.horvia.horvia.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.util.Patterns;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.horvia.horvia.R;
import com.horvia.horvia.models.User;
import com.horvia.horvia.ui.login.LoginActivity;
import com.horvia.horvia.models.Civility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private EditText etEmail, etPassword, etConfirmPassword, etFirstname, etLastname, etPhoneNumber;
    private Spinner spinCivility;

    private DatePicker dpBirthDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        etConfirmPassword = findViewById(R.id.confirm_password);
        etFirstname = findViewById(R.id.firstname);
        etLastname = findViewById(R.id.lastname);
        etPhoneNumber = findViewById(R.id.phone_number);
        dpBirthDate = findViewById(R.id.birth_date);

        spinCivility = findViewById(R.id.civility);

        SetCivilitySpinnerValues();
    }

    public void SwitchToLogin(View view) {
        TextView link = view.findViewById(R.id.login);
        SpannableString content = new SpannableString(link.getText().toString());
        content.setSpan(new UnderlineSpan(),0,link.length(),0);
        link.setText(content);

        Intent myIntent = new Intent(this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("Switch Civility", String.valueOf(position));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void OnSubmitRegister(View view){
        if(etEmail.length() == 0){
            etEmail.setError(this.getString(R.string.required_field));
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError(this.getString(R.string.invalid_email));
        }
        else if(etPassword.length() == 0){
            etPassword.setError(this.getString(R.string.required_field));
        }
        else if(etConfirmPassword.length() == 0){
            etConfirmPassword.setError(this.getString(R.string.required_field));
        }
        else if(!etConfirmPassword.getText().toString().equals(etPassword.getText().toString())){
            etConfirmPassword.setError(this.getString(R.string.password_should_be_same));
        }
        else if(etFirstname.length() == 0){
            etFirstname.setError(this.getString(R.string.required_field));
        }
        else if(etLastname.length() == 0){
            etLastname.setError(this.getString(R.string.required_field));
        }
        else if(etPhoneNumber.length() == 0){
            etPhoneNumber.setError(this.getString(R.string.required_field));
        }
        else if(!Pattern.compile("^[+]?[(]?[0-9]{3}[)]?[-\\s.]?[0-9]{3}[-\\s.]?[0-9]{4,6}$").matcher(etPhoneNumber.getText().toString()).matches()){
            etPhoneNumber.setError(this.getString(R.string.invalid_phone_number));
        }
        else{
            User user = new User();
            user.Email = etEmail.getText().toString();
            user.Password = etPassword.getText().toString();
            user.Cility = Civility.values()[spinCivility.getSelectedItemPosition()];
            user.FirstName = etFirstname.getText().toString();
            user.Lastname = etLastname.getText().toString();
            user.PhoneNumber = etPhoneNumber.getText().toString();
            user.BirthDate = new Date(dpBirthDate.getYear(),dpBirthDate.getMonth(),dpBirthDate.getDayOfMonth());

            Toast.makeText(this,"Champs valides",Toast.LENGTH_LONG).show();
            // TODO: Faire l'enregistrement dans la base de données
        }


    }

    private void SetCivilitySpinnerValues(){
        spinCivility.setOnItemSelectedListener(this);

        List<String> civilityList = new ArrayList<>();
        for (Civility civility : Civility.values()) {
            civilityList.add(getString(civility.getLabel()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, civilityList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinCivility.setSelection(0);
        spinCivility.setAdapter(adapter);
    }

}