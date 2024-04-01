package com.horvia.horvia.models;

import java.util.Date;

public class Order {
    /* PRIMARY KEY */
    public int Id;

    /* FOREIGN KEY */
    public int FarmId;
    public int UserId;

    /* PROPERTIES */
    public Date CreationDate;
    public Date CompletionDate;
    public OrderStatus Status;
    public Farm Farm;
    public User User;
}
