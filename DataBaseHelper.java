package com.example.sqlitedatabasetutorial;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String CUSTOMER_TABLE = "CUTOMER_TABLE";
    public static final String COLUMN_CUSTOMER_NAME = "CUSTOMER_NAME";
    public static final String COLUMN_CUSTOMER_AGE = "CUSTOMER_AGE";
    public static final String COLUMN_ACTIVE_CUSTOMER = "ACTIVE_CUSTOMER";
    private static final String COLUMN_CUSTOMER_ID = " CUSTOMER ID ";

    public DataBaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }
    
    //this is called the first time a database is accessed.
    //There should be code in here to create a new database.

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + CUSTOMER_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_CUSTOMER_NAME + " TEXT," +
                " " + COLUMN_CUSTOMER_AGE + " INT, " + COLUMN_ACTIVE_CUSTOMER + " BOOL " + COLUMN_CUSTOMER_ID  + " CUSTOMER_ID )";
        db.execSQL(createTableStatement);
    }

    //this is called if the database version number changes.
    //It prevents previous users apps from breaking when you change the database design.

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(CustomerModel customerModel) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_CUSTOMER_NAME, customerModel.getName());
        cv.put(COLUMN_CUSTOMER_AGE, customerModel.getAge());
        cv.put(COLUMN_ACTIVE_CUSTOMER, customerModel.isActive());

        long insert = db.insert(CUSTOMER_TABLE, null, cv);
        return insert != -1;
    }

    public void deleteOne(CustomerModel customerModel){
        // find customerModel in the database. if it is found, delete it, and return true.
        //if not found, return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM" + CUSTOMER_TABLE + " WHERE " + COLUMN_CUSTOMER_ID + " = " + customerModel.getId();

        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString, null);

        cursor.moveToFirst();
    }

    public List<CustomerModel> getEveryone(){
        List<CustomerModel> returnList = new ArrayList<>();

        //get data from the database
        String queryString  = "SELECT * FROM " + CUSTOMER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            //loop through the cursor (result set) and create new customer object. Put them into the return list.
            do{
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);

            }while (cursor.moveToNext());

        }
        else{
            System.out.println();//failure, do not add anything to the list.
        }

        //close both the cursor and the database when done.
        cursor.close();
        db.close();
        return returnList;
    }
}
