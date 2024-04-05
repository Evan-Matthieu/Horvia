package com.horvia.horvia.network;

import com.horvia.horvia.R;
import com.horvia.horvia.models.Category;
import com.horvia.horvia.models.Farm;
import com.horvia.horvia.models.Location;
import com.horvia.horvia.models.User;

import java.util.ArrayList;
import java.util.List;

public class ApiRequest {

    // USER REQUESTS

    public ApiResponseWithEntity<String> TryLogin(String username, String password){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponseWithEntity<String> CreateAccount(User user){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponse DeleteAccount(String userEmail){
        //TODO
        return new ApiResponse();
    }

    public ApiResponseWithEntity<User> UpdateUserInformations(User user){
        //TODO
        return new ApiResponseWithEntity<>();
    }



    // USER ADDRESS REQUESTS

    public ApiResponseWithEntity<User> CreateUserAddress(Location location){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponseWithEntity<Location> UpdateUserAddress(Location location){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponse DeleteUserAddress(int locationId){
        //TODO
        return new ApiResponse();
    }



    // FARM REQUEST

    public ApiResponseWithEntity<Farm> CreateFarm(Farm farm){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponseWithEntity<Farm> UpdateFarmInformations(Farm farm){
        //TODO
        return new ApiResponseWithEntity<>();
    }

    public ApiResponse DeleteFarm(int FarmId){
        //TODO
        return new ApiResponse();
    }



    // CATEGORY REQUEST

    public ApiResponseWithEntity<List<Category>> GetMainCategories(){
        List<Category> categoryies = new ArrayList<>();
        ApiResponseWithEntity<List<Category>> apiResponse = new ApiResponseWithEntity<>();
        apiResponse.Entity.add(new Category("Légumes","", 45, 584));
        apiResponse.Entity.add(new Category("Fruits","", 164, 584));
        apiResponse.Entity.add(new Category("Produits laitiers","", 8133, 584));
        apiResponse.Entity.add(new Category("Viandes","", 18, 584));


        //TODO
        return new ApiResponseWithEntity<>();
    }


}
