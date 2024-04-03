package com.horvia.horvia.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.MainActivity;
import com.horvia.horvia.R;
import com.horvia.horvia.models.DatabaseManager;
import com.horvia.horvia.ui.register.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private TextView errorConnectAccountTextView;
    private String email;
    private String password;

    private DatabaseManager databaseManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        errorConnectAccountTextView = findViewById(R.id.errorConnectAccountTextView);

        databaseManager = new DatabaseManager(getApplicationContext());
    }

    public void SwitchToRegister(View view) {
        TextView link = view.findViewById(R.id.register);
        SpannableString content = new SpannableString(link.getText().toString());
        content.setSpan(new UnderlineSpan(),0,link.length(),0);
        link.setText(content);

        Intent myIntent = new Intent(this, RegisterActivity.class);
        startActivity(myIntent);
        finish();
    }

    public void OnSubmitLogin(View view){
        if(etEmail.length() == 0){
            etEmail.setError(this.getString(R.string.required_field));
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString()).matches()){
            etEmail.setError(this.getString(R.string.invalid_email));
        }
        else if(etPassword.length() == 0){
            etPassword.setError(this.getString(R.string.required_field));
        }
        else{
            Toast.makeText(this,"Champs valides",Toast.LENGTH_LONG).show();

            email = etEmail.getText().toString();
            password = etPassword.getText().toString();

            connectUser();
        }

    }

    public void onApiResponse(JSONObject response) {
        Boolean success = null;
        String error = "";

        try {
            success = response.getBoolean("success");

            if (success) {
                Intent interfaceActivity = new Intent(getApplicationContext(), MainActivity.class);
                interfaceActivity.putExtra("email", email);
                startActivity(interfaceActivity);
                finish();

            } else {
                error = response.getString("error");
                errorConnectAccountTextView.setVisibility(View.VISIBLE);
                errorConnectAccountTextView.setText(error);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void connectUser() {


        String url = "https://api-horvia.foxysnake.com/api/action/connectUser.php";

        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                // Traitement de la réponse JSON en cas de succès
                onApiResponse(response);
                Toast.makeText(getApplicationContext(), "Opération réussie", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Gestion de l'erreur
                if (error.getMessage() != null && error.getMessage().contains("<br>")) {
                    Toast.makeText(getApplicationContext(), "Erreur de connexion. Veuillez réessayer plus tard.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        databaseManager.queue.add(jsonObjectRequest);
    }
}