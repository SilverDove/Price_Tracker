package com.example.hello_world;


public class Product {

    private String Name; // Stock the name of the product

    private String Link; // Stock the link of the corresponding product

    private boolean Priority; // Stock the priority level

    private int Numbers_Update; //Stock the numbers for the update ( for example every 5 hours here we stock 5)

    private int Time_Update; // Here we stock if it's hours(Time_update=1), days(Time_update=2), ...

    private boolean Notif_Under; // If the option is switch on so it's equal to one

    private double Price_Notif;

    private double Initial_price;

    private double Actual_price;

    private String Date_suivie;

    public Product(String pName, String pLink, double pInitial_price, String pDate_suivie){ // We create our product identity
        Name = pName;
        Link = pLink;
        Initial_price = pInitial_price;
        Actual_price = pInitial_price; // During the creation it's the same price as initial
        Date_suivie = pDate_suivie;


        Price_Notif=0;
        Priority = false; // Set by default
        Numbers_Update = 1; // Set by default
        Time_Update = 2; // Set by default
        Notif_Under = false; // Set by default
    }


    //*************   ACCESSEURS *************

    //Return the product's name
    public String getName()  { return Name; }

    //Return the link's product
    public String getLink() { return Link; }

    // Return the priority
    public boolean getPriority()
    {
        return Priority;
    }

    // Return Numbers_Update
    public int getNumbers_Update()
    {
        return Numbers_Update;
    }

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

    //Set the priority
    public void setPriority(boolean pPriority)
    {
        Priority = pPriority;
    }

    //Set Numbers_Update
    public void setNumbers_Update(int pNumbers_Update)
    {
        Numbers_Update = pNumbers_Update;
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

}
