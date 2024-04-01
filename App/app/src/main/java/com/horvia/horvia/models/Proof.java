package com.horvia.horvia.models;

import android.media.Image;

import java.io.File;

public class Proof {

    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int FarmId;

    /* PROPERTIES */
    public File Kbis;
    public File Sirene;
    public File OwnerShip;
    public Farm Farm;

}
