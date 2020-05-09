package com.example.hello_world;


public class Product {

    private String Name; // Stock the name of the product
    private String Link; // Stock the link of the corresponding product
    private int Time_Update; // notification evey X min (min 15min)
    private boolean Notif_Under; // If the option is switch on so it's equal to one
    private double Price_Notif;
    private double Initial_price;
    private double Actual_price;
    private String Date_suivie;
    private String last_Date;

    public Product(String pName, String pLink, double pInitial_price, String pDate_suivie){ // We create our product identity
        Name = pName;
        Link = pLink;
        Time_Update = 15; // Set by default (minutes)
        Notif_Under = false; // Set by default
        Price_Notif=0; //Set by default
        Initial_price = pInitial_price;
        Actual_price = pInitial_price; // During the creation it's the same price as initial
        Date_suivie = pDate_suivie;
        last_Date = pDate_suivie; //During the creation, it's the same date as data_suivie because it's the last time we checked the price on the website
    }

    //*************   ACCESSEURS *************

    //Return the product's name
    public String getName()  { return Name; }

    //Return the link's product
    public String getLink() { return Link; }

    // Return Time_Update
    public int getTime_Update()
    {
        return Time_Update;
    }

    //Return Notif_Under
    public boolean getNotif_Under()
    {
        return Notif_Under;
    }

    public double getPrice_Notif()
    {
        return Price_Notif;
    }

    //Return Initial_price
    public double getInitial_price()
    {
        return Initial_price;
    }

    //Return Actual_prive
    public double getActual_price()
    {
        return Actual_price;
    }

    //Return Date_suivie
    public String getDate_suivie()
    {
        return Date_suivie;
    }

    //Return last date
    public String getLast_Date() {
        return last_Date;
    }

    //*************   MUTATEURS   *************

    //Set the product's name
    public void setName(String pName)
    {
        Name = pName;
    }

    //Set the link's product
    public void setLink(String pLink)
    {
        Link = pLink;
    }

    //Set Time_Update
    public void setTime_Update(int pTime_Update)
    {
        Time_Update = pTime_Update;
    }

    //Set Notif_Under
    public void setNotif_Under(boolean pNotif_Under)
    {
        Notif_Under = pNotif_Under;
    }

    //Set price_notif
    public void setPrice_Notif(double pNotif)
    {
        Price_Notif = pNotif;
    }

    //Set Initial_price
    public void setInitial_price(double pInitial_price)
    {
        Initial_price = pInitial_price;
    }

    //Set Actual_price
    public void setActual_price(double pActual_price)
    {
        Actual_price = pActual_price;
    }

    //Set Date_suivie
    public void setDate_suivie(String pDate_suivie)
    {
        Date_suivie = pDate_suivie;
    }

    //Set Last_date
    public void setLast_Date(String last_Date) {
        this.last_Date = last_Date;
    }
}
