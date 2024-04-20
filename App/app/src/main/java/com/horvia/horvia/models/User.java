package com.horvia.horvia.models;


import android.graphics.Bitmap;

import java.util.Date;
import java.util.Locale;

public class User {
    public User(){

    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public short RoleId;

    public int LocationId;

    /* PROPERTIES */
    public String Lastname;
    public String FirstName;
    public String Email;
    public String PhoneNumber;
    public Role Role;
    public Location Location;
    public String Password;
    public Locale Language;
    public Date RegisterDate;
    public Date BirthDate;
    public Civility Civility;

    public Bitmap Picture;
}
