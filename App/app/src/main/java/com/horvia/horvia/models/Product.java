package com.horvia.horvia.models;

import android.media.Image;

public class Product {
    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int FarmId;

    /* PROPERTIES */
    public String Name;
    public String Description;
    public float UnitPrice;
    public MeasuringUnit MeasuringUnit;
    public Image Picture;
}
