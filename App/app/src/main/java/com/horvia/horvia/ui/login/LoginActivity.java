package com.horvia.horvia.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannedString;
import android.text.style.UnderlineSpan;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.horvia.horvia.R;
import com.horvia.horvia.ui.register.RegisterActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
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
            // TODO: Faire la vérification de connexion
        }
    }
}