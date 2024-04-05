package com.horvia.horvia.models;

import java.sql.Time;
import java.time.DayOfWeek;

public class OpeningHours {
    public OpeningHours(){

    }

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int FarmId;

    /* PROPERTIES */
    public DayOfWeek Day;
    public Time OpenAM;
    public Time CloseAM;
    public Time OpenPM;
    public Time ClosePM;
    public Farm Farm;


}
