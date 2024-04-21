package com.horvia.horvia.network;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Civility;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.models.MeasuringUnit;
import com.horvia.horvia.models.Order;
import com.horvia.horvia.models.OrderStatus;
import com.horvia.horvia.models.Product;
import com.horvia.horvia.utils.BitmapUtil;
import com.horvia.horvia.utils.DatabaseManager;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.User;
import com.horvia.horvia.utils.pagination.PaginationParams;
import com.horvia.horvia.utils.pagination.PaginationResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ApiRequest {

    private final String API_URL = "https://api-horvia.foxysnake.com/api";
    private Context _context;
    private DatabaseManager _databaseManager;
    private String _jwtToken;

    public ApiRequest(Context context){
        _context = context;
        _databaseManager = new DatabaseManager(context);

        SharedPreferences sharedPreferences = context.getSharedPreferences("User_Login", Context.MODE_PRIVATE);
        _jwtToken = sharedPreferences.getString("jwtToken", null);
        Log.d("token", _jwtToken != null ? _jwtToken : "null");
    }


    // USER REQUESTS

    public void GetUsers(ApiRequestListener<User> callback){
        String url = API_URL + "/action/getUsers.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    JSONObject entity = response.getJSONObject("entity");


                    User user = new User();

                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.FRENCH);

                    user.FirstName = entity.getString("firstname");
                    user.Lastname = entity.getString("lastname");
                    user.Email = entity.getString("email");
                    user.PhoneNumber = entity.getString("phone");
                    user.BirthDate = formatter.parse(entity.getString("birth_date"));
                    user.Picture = BitmapUtil.StringToBitmap(entity.getString("picture"));
                    user.Civility = Civility.valueOf(entity.getString("civility"));


                    callback.onComplete(user, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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

    public void UpdateUserPersonnalInformations(User user,ApiRequestListener<String> callback){
        String url = API_URL + "/action/modifyUser.php";
        Map<String, String> params = new HashMap<>();
        params.put("firstname", user.FirstName);
        params.put("lastname", user.Lastname);
        params.put("email", user.Email);
        params.put("phone", user.PhoneNumber);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        params.put("birth", formatter.format(user.BirthDate));
        params.put("civility", user.Civility.toString());
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, response -> {
            try {
                if(response.getBoolean("success")){
                    callback.onComplete(response.getString("entity"), null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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





    public void GetAddresses(ApiRequestListener<Location> callback){
        String url = API_URL + "/action/getAddresses.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    JSONObject entity = response.getJSONObject("entity");


                    Location location = new Location();

                    location.Address = entity.getString("address");
                    location.City = entity.getString("city");
                    location.ZipCode = entity.getString("zip_code");
                    location.FurtherDetails = entity.getString("further_details");

                    callback.onComplete(location, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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


    public void UpdateUserAddress(Location location,ApiRequestListener<String> callback){
        String url = API_URL + "/action/modifyAddresses.php";
        Map<String, String> params = new HashMap<>();
        params.put("address", location.Address);
        params.put("city", location.City);
        params.put("zipCode", location.ZipCode);
        params.put("further_details", location.FurtherDetails);
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, parameters, response -> {
            try {
                if(response.getBoolean("success")){
                    callback.onComplete("", null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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




    // USER REQUESTS

    public void TryLogin(String email, String password, ApiRequestListener<String> callback){
        Map<String, String> params = new HashMap<>();
        params.put("email", email);
        params.put("password", password);
        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, API_URL + "/action/connectUser.php", parameters, response -> {
            if (response != null) {
                if (response.optBoolean("success")) {
                    String token = response.optString("entity");
                    callback.onComplete(token, null);
                } else {
                    callback.onComplete(null, _context.getResources().getString(R.string.wrong_credentials));
                }
            } else {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
            }
        }, error -> {
            callback.onComplete(null, _context.getResources().getString(R.string.retry_later));
        });

        _databaseManager.queue.add(jsonObjectRequest);
    }

    public void CreateAccount(User user){
        //TODO
    }

    public void ChangePassword(String currentPassword, String newPassword, ApiRequestListener<Boolean> callback){
        // TODO faire la requete à l'API

    }



    // USER ADDRESS REQUESTS


    // Farm Request

    public void GetFarms(PaginationParams paginationParams, @Nullable ArrayList<Integer> categoriesId, ApiRequestListener<PaginationResult<Farm>> callback){
        String url = API_URL + "/action/getFarms.php?page_size=" + paginationParams.PageSize + "&page_number=" + paginationParams.PageNumber;
        if(paginationParams.Query != null) url = url + "&query=" + paginationParams.Query;
        if(categoriesId != null && categoriesId.size() > 0) url = url + "&cId=" + categoriesId.toString();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    PaginationResult<Farm> result = new PaginationResult<Farm>();

                    JSONObject entity = response.getJSONObject("entity");

                    JSONArray items = entity.getJSONArray("items");
                    result.TotalCount = entity.getInt("totalCount");

                    for(int i = 0; i < items.length(); i++){
                        JSONObject object = items.getJSONObject(i);

                        Location location = new Location();
                        location.Address = object.getString("address");
                        location.ZipCode = object.getString("zipCode");
                        location.City = object.getString("city");

                        Farm farm = new Farm();
                        farm.Id = object.getInt("id");
                        farm.Name = object.getString("name");
                        farm.Description = object.getString("description");
                        farm.Rate = object.getString("rate").equals("null") ? null : Float.parseFloat(object.getString("rate"));
                        farm.RateNumber = object.getInt("rate_number");
                        farm.Picture = BitmapUtil.StringToBitmap(object.getString("picture"));
                        farm.Location = location;

                        for (String id : object.getString("categories").split(",")) {
                            farm.Categories.add(new Category(parseInt(id)));
                        }

                        result.Items.add(farm);
                    }

                    callback.onComplete(result, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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

    public void GetMainCategories(ApiRequestListener<ArrayList<Category>> callback){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL + "/action/getMainCategories.php", new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    ArrayList<Category> categories = new ArrayList<>();

                    JSONArray entity = response.getJSONArray("entity");

                    for(int i = 0; i < entity.length(); i++){
                        JSONObject object = entity.getJSONObject(i);

                        Category category = new Category();
                        category.Name = object.getString("name");
                        category.Picture = BitmapUtil.StringToBitmap(object.getString("picture"));
                        category.FarmNumber = object.getInt("farm_count");

                        categories.add(category);
                    }

                    callback.onComplete(categories, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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

    public void GetFarmCategories(int farmId, ApiRequestListener<ArrayList<Category>> callback){
        String url = API_URL + "/action/getFarmCategories.php?id=" + farmId;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    ArrayList<Product> products = new ArrayList<>();

                    JSONArray entity = response.getJSONArray("entity");

                    for(int i = 0; i < entity.length(); i++){
                        JSONObject object = entity.getJSONObject(i);

                        Category category = new Category();
                        category.Name = object.getString("name");

                        Product product = new Product();
                        product.Name = object.getString("productName");
                        product.Description = object.getString("productDescription");
                        product.UnitPrice = Float.parseFloat(object.getString("productUnitPrice"));
                        Log.d("measuringUnit", object.getString("productMeasuringUnit"));
                        product.MeasuringUnit = MeasuringUnit.valueOf(object.getString("productMeasuringUnit"));
                        product.Picture = BitmapUtil.StringToBitmap(object.getString("productPicture"));
                        product.Category = category;
                        products.add(product);
                    }

                    Map<String, Category> categoryMap = new HashMap<>();

                    for (Product product : products) {
                        String categoryName = product.Category.Name;
                        Category category = categoryMap.get(categoryName);
                        if (category == null) {
                            category = new Category();
                            category.Name = categoryName;
                            categoryMap.put(categoryName, category);
                        }
                        category.Products.add(product);
                    }
                    ArrayList<Category> categories = new ArrayList<>(categoryMap.values());
                    callback.onComplete(categories, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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


    // ORDERS REQUEST


    public void GetOrders(ApiRequestListener<ArrayList<Order>> callback){
        String url = API_URL + "/action/getLastOrders.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, new JSONObject(), response -> {
            try {
                if(response.getBoolean("success")){
                    ArrayList<Order> orders = new ArrayList<>();

                    JSONArray items = response.getJSONArray("entity");

                    for(int i = 0; i < items.length(); i++){
                        JSONObject object = items.getJSONObject(i);

                        Farm farm = new Farm();
                        farm.Picture = BitmapUtil.StringToBitmap(object.getString("picture"));

                        Order order = new Order();
                        order.Id = object.getInt("order_id");
                        order.Status = OrderStatus.valueOf(object.getString("status"));
                        order.TotalPrice = Float.parseFloat(object.getString("total_price"));
                        order.Farm = farm;

                        orders.add(order);
                    }

                    callback.onComplete(orders, null);
                }
                else{
                    callback.onComplete(null, response.getString("error"));
                }
            } catch (JSONException e) {
                callback.onComplete(null, _context.getResources().getString(R.string.error_occured));
                e.printStackTrace();
            }
        }, error -> {
            callback.onComplete(null, String.valueOf(error));
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


}
