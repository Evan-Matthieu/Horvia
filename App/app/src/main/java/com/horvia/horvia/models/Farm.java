package com.horvia.horvia.models;

import android.media.Image;

import java.io.File;
import java.util.List;

public class Farm {
    public Farm(){

    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int LocationId;

    /* PROPERTIES */
    public String Name;
    public Image Picture;
    public Location Location;
    public List<Category> Categories;
    public List<Product> Products;
    public List<Review> Reviews;
    public List<OpeningHours> OpeningHours;
    public List<ExceptionalClosure> ExceptionalClosures;
    public float Rate;
    public String Description;
}
