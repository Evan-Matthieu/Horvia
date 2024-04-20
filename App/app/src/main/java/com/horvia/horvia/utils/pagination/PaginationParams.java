package com.horvia.horvia.utils.pagination;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PaginationParams {


    public PaginationParams(int pageSize, int pageNumber, String query){
        this.PageSize = pageSize;
        this.PageNumber = pageNumber;
        this.Query = query;
    }
    public PaginationParams(){ }

    public int PageSize = 20;
    public int PageNumber = 1;

    public String Query = null;
}
