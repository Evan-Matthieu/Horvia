package com.horvia.horvia.models;

public class Location {

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int UserId;

    /* PROPERTIES */
    public User User;
    public String Address;
    public String ZipCode;
    public String City;
}
