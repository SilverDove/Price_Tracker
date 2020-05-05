package com.example.hello_world;

public class ProductItem {
    /*All variables display in the list*/
    private String mProductName;
    private String mProductPrice; //NOT SURE IF DOUBLE

    public ProductItem(String productName, String productPrice){
        mProductName=productName;
        mProductPrice=productPrice;
    }

    public String getProductName(){
        return mProductName;
    }

    public String getProductPrice(){
        return mProductPrice;
    }

}
