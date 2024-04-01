package com.horvia.horvia.models;

public class Review {

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int UserId;
    public int FarmId;

    /* PROPERTIES */
    public float Rate;
    public String Comment;
    public User User;
    public Farm Farm;
}
