package com.horvia.horvia.utils.pagination;

import java.util.ArrayList;

public class PaginationResult<T> {

    public PaginationResult(ArrayList<T> items, int totalCount){
        this.Items = items;
        this.TotalCount = totalCount;
    }

    public PaginationResult(){ }
    public ArrayList<T> Items = new ArrayList<>();
    public int TotalCount = 0;
}
