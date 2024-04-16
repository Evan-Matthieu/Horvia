package com.horvia.horvia.network;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.MainActivity;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.utils.BitmapUtil;
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + "/action/connectUser.php", parameters, response -> {
            try {
                if(response.getBoolean("success")){
                    callback.onComplete(response.getString("entity"), null);
                }
                else{
                    callback.onComplete(null, _context.getResources().getString(R.string.wrong_credentials));
                }

            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
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


public void CreateFarm(Farm farm , ApiRequestListener<String> callback) {



    Map<String, String> params = new HashMap<>();
    params.put("name", farm.Name);
    params.put("description", farm.Description);
    params.put("category", farm.Description);
    params.put("address", farm.Location.Address);
    params.put("zip_code", farm.Location.ZipCode);
    params.put("city", farm.Location.City);
    params.put("picture", BitmapUtil.BitmapToString(farm.Picture));
    JSONObject parameters = new JSONObject(params);

    Log.d("Photo:", BitmapUtil.BitmapToString(farm.Picture));

    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + "/action/createFarm.php", parameters, response -> {
        try {
            if(response.getBoolean("success")){
                callback.onComplete(null, null);
            }
            else{
                callback.onComplete(null, response.getString("error"));
            }

        } catch (JSONException e) {
            callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
            e.printStackTrace();
        }
    }, error -> {
        callback.onComplete(null, _context.getResources().getString(R.string.retry_later));
    }) {
        @Override
        public Map<String, String> getHeaders() {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + _jwtToken);
            return headers;
        }
    };
    _databaseManager.queue.add(jsonObjectRequest);
}



    // CATEGORY REQUEST

    public void GetMainCategories(){
        List<Category> categoryies = new ArrayList<>();
        categoryies.add(new Category("Légumes","", 45, 584));
        categoryies.add(new Category("Fruits","", 164, 584));
        categoryies.add(new Category("Produits laitiers","", 8133, 584));
        categoryies.add(new Category("Viandes","", 18, 584));

    }



}
