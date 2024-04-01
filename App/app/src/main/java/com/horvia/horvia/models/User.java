package com.horvia.horvia.models;


import java.util.Date;
import java.util.Locale;

public class User {

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public short RoleId;

    /* PROPERTIES */
    public String Lastname;
    public String FirstName;
    public String Email;
    public String Phone;
    public Role Role;
    public String Password;
    public Locale Language;
    public Date RegisterDate;
    public Date BirthDate;
    public Civility Cility;
}
