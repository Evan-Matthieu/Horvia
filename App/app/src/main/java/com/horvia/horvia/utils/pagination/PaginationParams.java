package com.horvia.horvia.utils.pagination;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PaginationParams {


    public PaginationParams(int pageNumber, int pageSize,String query){
        this.PageNumber = pageNumber;
        this.PageSize = pageSize;
        this.Query = query;
    }
    public PaginationParams(){ }

    public int PageSize = 20;
    public int PageNumber = 1;

    public String Query = null;
}
