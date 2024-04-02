package com.horvia.horvia.network;

import java.util.List;

public class ApiResponse<T> {
    public List<String> Errors;

    public T Entity;

    public boolean isSuccess(){
        return Errors.isEmpty();
    }
}
