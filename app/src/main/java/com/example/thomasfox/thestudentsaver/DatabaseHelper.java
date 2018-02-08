package com.example.thomasfox.thestudentsaver;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by thomasfox on 18/11/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Discounts.db";
    private static final int DATABASE_VERSION = 1;
    public static final String DISCOUNT_TABLE_NAME = "Discounts";
    public static final String DISCOUNT_COLUMN_ID = "discount_id";
    public static final String DISCOUNT_NAME = "discount_name";
    public static final String DISCOUNT_DESCRIPTION = "discount_description";
    public static final String DISCOUNT_CODE = "discount_code";
    public static final String CLIENT_NAME = "discount_client_name";
    public static final String DISCOUNT_STARTDATE = "discount_start_date";
    public static final String DISCOUNT_ENDDATE = "discount_end_date";


    // create table sql query
    private String CREATE_DISCOUNT_TABLE = "CREATE TABLE IF NOT EXISTS " + DISCOUNT_TABLE_NAME + " ("
            + DISCOUNT_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DISCOUNT_NAME + " TEXT,"
            + DISCOUNT_DESCRIPTION + " TEXT,"
            + DISCOUNT_CODE + " TEXT,"
            + CLIENT_NAME  + " STRING,"
            + DISCOUNT_STARTDATE + " DATETIME,"
            + DISCOUNT_ENDDATE + " DATETIME" + ");";

    //Mcdonalds
    private String INSERT1 = "INSERT INTO " + DISCOUNT_TABLE_NAME + " VALUES ('1' , 'FreeBurger' , 'Enjoy a free Cheeseburger, Mayo Chicken or McFlurry Original® when you order an Extra Value or Wrap Meal at McDonalds.', 'Show Student ID for Discount', 'Mcdonalds','01/01/2018', '01/01/2040');";

    //Topshop
    private String INSERT2 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('2', '10% off', 'Enjoy 10% Student Discount when you shop with Topshop in-store.', 'Show Student ID for Discount', 'Topshop', '01/01/2018', '01/01/2040');";

    //Accessorize
    private String INSERT3 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('3', '30% off', 'Enjoy up to 30% Off sale items + an extra 10% Off when you shop with Accessorize in-store.', 'Show Student ID for Discount', 'Accessorize', '01/01/2018', '26/11/2018');";

    //Ann Summers
    private String INSERT4 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('4', '10% off', 'Enjoy 10% Student Discount when you shop with Ann Summers in-store.', 'Show Student ID for Discount', 'Ann Summers', '01/01/2018', '01/01/2040');";

    //Burton
    private String INSERT5 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('5', '10% off', 'Enjoy 10% Student Discount when you shop with Burton in-store.', 'Show Student ID for Discount', 'Burton', '01/01/2018', '01/01/2040');";

    //Oasis
    private String INSERT6 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('6', '10% off', 'Enjoy 10% Student Discount when you shop with Oasis in-store.', 'Show Student ID for Discount', 'Oasis', '01/01/2018', '01/01/2040');";

    //USC
    private String INSERT7 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('7', '10% off', 'Enjoy 10% Student Discount when you shop with USC in-store.', 'Show Student ID for Discount', 'USC', '10/01/2018', '01/01/2040');";

    //FootAsylum
    private String INSERT8 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('8', '10% off', 'Enjoy 10% Student Discount when you shop with FootAsylum in-store.', 'Show Student ID for Discount', 'FootAsylum', '10/01/2018', '01/01/2040');";

    //Schuh
    private String INSERT9 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('9', '10% off', 'Enjoy 10% Student Discount when you shop with Schuh in-store.', 'Show Student ID for Discount', 'Schuh', '10/01/2018', '01/01/2040');";

    //Select
    private String INSERT10 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('10', '20% off', 'Enjoy 20% Student Discount when you shop with Select in-store.', 'Show Student ID for Discount', 'Select', '11/01/2018', '01/01/2040');";

    //Miss Selfridge
    private String INSERT11 = "INSERT INTO " + DISCOUNT_TABLE_NAME + "  VALUES ('11', '10% off', 'Enjoy 10% Student Discount when you shop with Miss Selfridge in-store.', 'Show Student ID for Discount', 'Miss Selfridge', '10/01/2018', '01/01/2040');";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    //Execute the following statements on creation of the database.
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_DISCOUNT_TABLE);
        db.execSQL(INSERT1);
        db.execSQL(INSERT2);
        db.execSQL(INSERT3);
        db.execSQL(INSERT4);
        db.execSQL(INSERT5);
        db.execSQL(INSERT6);
        db.execSQL(INSERT7);
        db.execSQL(INSERT8);
        db.execSQL(INSERT9);
        db.execSQL(INSERT10);
        db.execSQL(INSERT11);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DISCOUNT_TABLE_NAME);
        onCreate(db);
    }

    // Get All Discounts From Database To Display On Discount Page

    public List<Discounts> getAllDiscounts() {
        // array of columns to fetch
        String[] columns = {
                DISCOUNT_DESCRIPTION,
                DISCOUNT_CODE,
                CLIENT_NAME
        };

        // sorting orders
        String sortOrder = CLIENT_NAME + " ASC";

        List<Discounts> discountList = new ArrayList<Discounts>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the  table
        Cursor cursor = db.query(
                DISCOUNT_TABLE_NAME, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {

            do {
                Discounts discount = new Discounts();
                discount.setclientName(cursor.getString(cursor.getColumnIndex(CLIENT_NAME)));
                discount.setDescription(cursor.getString(cursor.getColumnIndex(DISCOUNT_DESCRIPTION)));
                discount.setDiscountCode(cursor.getString(cursor.getColumnIndex(DISCOUNT_CODE)));

                discountList.add(discount);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return discountList;
    }


    // Latest Discounts To Display On Homepage


    public List<Discounts> getNewDiscounts() {
        // array of columns to fetch
        String[] columns = {
                DISCOUNT_DESCRIPTION,
                DISCOUNT_CODE,
                CLIENT_NAME
        };

        // sorting orders
        String sortOrder = CLIENT_NAME + " ASC";

        List<Discounts> newdiscountList = new ArrayList<Discounts>();


        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table

        String[] args = {"10/01/2018"};

        Cursor cursor1 = db.query(
                DISCOUNT_TABLE_NAME, //Table to query
                columns,    //columns to return
                DISCOUNT_STARTDATE+">=?",        //columns for the WHERE clause
                args,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor1.moveToFirst()) {

            do {
                Discounts newdiscount = new Discounts();
                newdiscount.setclientName(cursor1.getString(cursor1.getColumnIndex(CLIENT_NAME)));
                newdiscount.setDescription(cursor1.getString(cursor1.getColumnIndex(DISCOUNT_DESCRIPTION)));
                newdiscount.setDiscountCode(cursor1.getString(cursor1.getColumnIndex(DISCOUNT_CODE)));
                newdiscountList.add(newdiscount);
            } while (cursor1.moveToNext());
        }

        cursor1.close();
        db.close();


        return newdiscountList;
    }



}
