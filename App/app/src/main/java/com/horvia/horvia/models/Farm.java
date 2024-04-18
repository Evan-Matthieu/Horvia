package com.horvia.horvia.models;

import android.graphics.Bitmap;
import android.media.Image;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.ArrayList;
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
    public Bitmap Picture;
    public Location Location;
    public ArrayList<Category> Categories = new ArrayList<>();
    public ArrayList<Product> Products = new ArrayList<>();
    public ArrayList<Review> Reviews = new ArrayList<>();
    public ArrayList<OpeningHours> OpeningHours = new ArrayList<>();
    public ArrayList<ExceptionalClosure> ExceptionalClosures = new ArrayList<>();
    @Nullable
    public Float Rate;
    public int RateNumber;
    public String Description;
}
