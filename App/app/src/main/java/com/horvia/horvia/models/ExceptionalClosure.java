package com.horvia.horvia.models;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

public class ExceptionalClosure {
    public ExceptionalClosure(){

    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int FarmId;

    /* PROPERTIES */
    public Date Date;
    public Time OpenAM;
    public Time CloseAM;
    public Time OpenPM;
    public Time ClosePM;
    public Farm Farm;
}
