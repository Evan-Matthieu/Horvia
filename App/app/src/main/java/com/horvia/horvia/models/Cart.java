package com.horvia.horvia.models;

import android.graphics.Picture;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Dictionary;

public class Cart {

    public Cart(){

    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int ProductId;

    /* PROPERTIES */
    public Product Product;

    public double Quantity;
}
