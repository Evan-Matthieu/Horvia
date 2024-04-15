package com.horvia.horvia.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.MainActivity;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.utils.DatabaseManager;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiRequest {

    private final String API_URL = "https://api-horvia.foxysnake.com/api";
    private Context _context;
    private DatabaseManager _databaseManager;
    private String _jwtToken;

    public ApiRequest(Context context){
        _context = context;
        _databaseManager = new DatabaseManager(context);

        SharedPreferences sharedPreferences = _context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        _jwtToken = sharedPreferences.getString("jwtToken", "");
    }


    // USER REQUESTS

    public void TryLogin(String email, String password, ApiRequestListener<String> callback){


        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);
        Log.d("test", "1");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + "/action/connectUser.php", parameters, response -> {
            Log.d("test", "2");
            try {
                Log.d("success", String.valueOf(response.getBoolean("success")));
                Log.d("entity", response.getString("entity"));
                if(response.getBoolean("success")){
                    callback.onComplete(response.getString("entity"), null);
                }
                else{
                    callback.onComplete(null, _context.getResources().getString(R.string.wrong_credentials));
                }

            } catch (JSONException e) {
                Log.d("error", "salope1");
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            Log.d("error", "salope2");
            callback.onComplete(null, _context.getResources().getString(R.string.retry_later));
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + _jwtToken);
                return headers;
            }
        };
        Log.d("test", "5");
        _databaseManager.queue.add(jsonObjectRequest);
    }

    public void CreateAccount(User user){
        //TODO
    }



    // USER ADDRESS REQUESTS





    // FARM REQUEST





    // CATEGORY REQUEST

    public void GetMainCategories(){
        List<Category> categoryies = new ArrayList<>();
        categoryies.add(new Category("Légumes","", 45, 584));
        categoryies.add(new Category("Fruits","", 164, 584));
        categoryies.add(new Category("Produits laitiers","", 8133, 584));
        categoryies.add(new Category("Viandes","", 18, 584));

    }



}
