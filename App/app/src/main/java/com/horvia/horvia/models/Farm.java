package com.horvia.horvia.models;

public class Farm {

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int LocationId;

    /* PROPERTIES */
    public String Name;
    public Location Location;
    public float Rate;
    public String Description;
}
