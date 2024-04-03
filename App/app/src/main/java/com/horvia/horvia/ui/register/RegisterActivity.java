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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.R;
import com.horvia.horvia.models.DatabaseManager;
import com.horvia.horvia.models.User;
import com.horvia.horvia.ui.login.LoginActivity;
import com.horvia.horvia.models.Civility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.text.SimpleDateFormat;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private User user;
    private EditText etEmail, etPassword, etConfirmPassword, etFirstname, etLastname, etPhoneNumber;
    private Spinner spinCivility;

    private DatePicker dpBirthDate;
    private TextView errorCreateAccountTextView;
    private DatabaseManager databaseManager;

    private String lastname,firstname,password,email,phone,civility;
    private String birth;




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
        errorCreateAccountTextView = findViewById(R.id.errorCreateAccountTextView);

        spinCivility = findViewById(R.id.civility);

        SetCivilitySpinnerValues();

        databaseManager = new DatabaseManager(getApplicationContext());
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


            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            civility = String.valueOf(Civility.values()[spinCivility.getSelectedItemPosition()]);
            firstname = etFirstname.getText().toString();
            lastname = etLastname.getText().toString();
            phone = etPhoneNumber.getText().toString();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            birth = formatter.format(new Date((dpBirthDate.getYear() - 1900),dpBirthDate.getMonth(),dpBirthDate.getDayOfMonth()));
            Toast.makeText(this,"Champs valides",Toast.LENGTH_LONG).show();
            // TODO: Faire l'enregistrement dans la base de données
            createAccount();
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

    public void onApiResponse(JSONObject response) {
        Boolean success = null;
        String error = "";

        try {
            success = response.getBoolean("success");

            if (success) {
                Intent interfaceActivity = new Intent(this, LoginActivity.class);
                interfaceActivity.putExtra("email", email);
                startActivity(interfaceActivity);
                finish();
            } else {
                error = response.getString("error");
                errorCreateAccountTextView.setVisibility(View.VISIBLE);
                errorCreateAccountTextView.setText(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void createAccount() {


        String url = "https://api-horvia.foxysnake.com/api/action/createAccount.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("lastname", lastname);
        params.put("firstname", firstname);
        params.put("phone", phone);
        params.put("birth", birth);
        params.put("civility", civility);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                onApiResponse(jsonObject);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        databaseManager.queue.add(jsonObjectRequest);
    }
}