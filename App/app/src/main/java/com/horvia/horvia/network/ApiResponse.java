package com.horvia.horvia.network;

import java.util.List;

public class ApiResponse {
    public List<String> Errors;

    public boolean isSuccess(){
        return Errors.isEmpty();
    }
}
