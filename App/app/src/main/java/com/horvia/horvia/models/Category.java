package com.horvia.horvia.models;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Category {
    public Category(){ }

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
    public ArrayList<Product> Products = new ArrayList<>();
    public int ProductsNumber;
    public ArrayList<Farm> Farm = new ArrayList<>();
    public int FarmNumber;


}
