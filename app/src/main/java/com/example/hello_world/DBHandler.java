package com.example.hello_world;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "ProductInfo";
    // Contacts table name
    private static final String TABLE_PRODUCTS = "products";
    // Shops Table Columns names
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_LINK = "link";
    private static final String PRODUCT_IP = "initial_price";
    private static final String PRODUCT_AP = "actual_price";
    private static final String PRODUCT_DS = "date_suivie";
    private static final String PRODUCT_PRIORITY = "priority";
    private static final String PRODUCT_NU = "notif_under";
    private static final String PRODUCT_NUMBERS_U = "numbers_update";
    private static final String PRODUCT_TIME_U = "time_update";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { // We create the database
        String CREATE_CONTACTS_TABLE = " CREATE TABLE " + TABLE_PRODUCTS + " ( "
                + PRODUCT_NAME + " TEXT NOT NULL , " + PRODUCT_LINK + " TEXT NOT NULL, "
                + PRODUCT_IP + " INTEGER NOT NULL, " + PRODUCT_AP + " INTEGER NOT NULL, "
                + PRODUCT_DS + " TEXT NOT NULL, " + PRODUCT_PRIORITY + " INTEGER NOT NULL, " +
                PRODUCT_NU + " INTEGER NOT NULL, " + PRODUCT_NUMBERS_U + " INTEGER NOT NULL, " +
                PRODUCT_TIME_U + " INTEGER NOT NULL " + " ) ";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL(" DROP TABLE " + TABLE_PRODUCTS + " ; ");
        // Creating tables again
        onCreate(db);
    }

    // Adding new product
    public void addProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();// Opening database connection
        ContentValues values = new ContentValues();

        values.put(PRODUCT_NAME, product.getName()); // Product Name
        values.put(PRODUCT_LINK, product.getLink()); // Product Link
        values.put(PRODUCT_IP, product.getInitial_price()); // Product Initial_price
        values.put(PRODUCT_AP, product.getActual_price()); // Product Actual_price
        values.put(PRODUCT_DS, product.getDate_suivie()); // Product Date_suivie
        values.put(PRODUCT_PRIORITY, product.getPriority()); // Product priority
        values.put(PRODUCT_NU, product.getNotif_Under()); // Product Notif_under
        values.put(PRODUCT_NUMBERS_U, product.getNumbers_Update()); // Product numbers update
        values.put(PRODUCT_TIME_U, product.getTime_Update()); // Product time update


        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting one product
    public Product getProduct(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PRODUCTS, new String[]{PRODUCT_NAME,
                        PRODUCT_LINK, PRODUCT_IP, PRODUCT_AP, PRODUCT_DS, PRODUCT_PRIORITY, PRODUCT_NU, PRODUCT_NUMBERS_U, PRODUCT_TIME_U}, PRODUCT_NAME + "=?",
                new String[]{String.valueOf(name)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Product contact = new Product((cursor.getString(0)),
                cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(4));

        contact.setActual_price(Double.parseDouble(cursor.getString(3)));
        contact.setPriority(Boolean.parseBoolean(cursor.getString(5)));
        contact.setNotif_Under(Boolean.parseBoolean(cursor.getString(6)));
        contact.setNumbers_Update(Integer.parseInt(cursor.getString(7)));
        contact.setTime_Update(Integer.parseInt(cursor.getString(8)));


        // return product
        return contact;
    }

    // Getting products Count
    public int getProductsCount() {
        String countQuery = " SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    // Getting All Products
    public List <Product> getAllProducts() {
        List <Product> productList = new ArrayList <Product>();
        // Select All Query
        String selectQuery = " SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getString(0), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(4));
                product.setActual_price(Double.parseDouble(cursor.getString(3)));
                product.setPriority(Boolean.parseBoolean(cursor.getString(5)));
                product.setNotif_Under(Boolean.parseBoolean(cursor.getString(6)));
                product.setNumbers_Update(Integer.parseInt(cursor.getString(7)));
                product.setTime_Update(Integer.parseInt(cursor.getString(8)));

                // Adding contact to list
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close(); // Closing database connection
        // return contact list
        return productList;
    }

    // Deleting a product
    public void deleteProduct(Product product) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PRODUCTS, PRODUCT_NAME + " = ? ",
                new String[]{String.valueOf(product.getName())});
        db.close();
    }


    public void AllDelete() {

        // Select All Query
        String selectQuery = " SELECT * FROM " + TABLE_PRODUCTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product(cursor.getString(0), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(4));

                deleteProduct(product);



            } while (cursor.moveToNext());
        }
        cursor.close();


    }
}
