package com.horvia.horvia.models;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.File;
import java.util.List;

public class Category {
    public Category(String name, String description){
        this.Name = name;
        this.Description = description;
    }

    public Category(String name, String description, int productNumber, int farmNumber){
        this.Name = name;
        this.Description = description;
        this.ProductsNumber = productNumber;
        this.FarmNumber = farmNumber;
    }

    public Category(int id){
        this.Id = id;
    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */


    /* PROPERTIES */
    public String Name;
    public String Description;
    public Bitmap Picture;
    public List<Product> Products;
    public int ProductsNumber;
    public List<Farm> Farm;
    public int FarmNumber;


}
