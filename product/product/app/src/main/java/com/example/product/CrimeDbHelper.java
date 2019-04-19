package com.example.product;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextThemeWrapper;

public class CrimeDbHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "CRIMEINFO.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_QUERY =
     "CREATE TABLE "+ Userdata.NewUserInfo.Table_name+"("+ Userdata.NewUserInfo.name+" TEXT,"+ Userdata.NewUserInfo.reference+" TEXT,"+ Userdata.NewUserInfo.type+" TEXT,"+ Userdata.NewUserInfo.date+" TEXT,"+ Userdata.NewUserInfo.time+" TEXT,"+ Userdata.NewUserInfo.location+ " TEXT,"+ Userdata.NewUserInfo.description+" TEXT);";

    public CrimeDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS","Database created / opened...");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS","Table created...");

    }

    public void addinformation(String Name,String Reference,String Type,String Date,String Time,String Location,String Description,SQLiteDatabase db)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Userdata.NewUserInfo.name,Name);
        contentValues.put(Userdata.NewUserInfo.reference,Reference);
        contentValues.put(Userdata.NewUserInfo.type,Type);
        contentValues.put(Userdata.NewUserInfo.date,Date);
        contentValues.put(Userdata.NewUserInfo.time,Time);
        contentValues.put(Userdata.NewUserInfo.location,Location);
        contentValues.put(Userdata.NewUserInfo.description,Description);
        db.insert(Userdata.NewUserInfo.Table_name,null,contentValues);
        Log.e("DATABASE OPERATIONS","One row inserted...");
    }

    public Cursor getinformation(SQLiteDatabase db)
    {
        Cursor cursor;
        String[] projections = {
                Userdata.NewUserInfo.name,
                Userdata.NewUserInfo.reference,
                Userdata.NewUserInfo.type,
                Userdata.NewUserInfo.date,
                Userdata.NewUserInfo.time,
                Userdata.NewUserInfo.location,
                Userdata.NewUserInfo.description,

        };

       cursor = db.query(Userdata.NewUserInfo.Table_name,projections,null,null,null,null,null);
       return cursor;


    }


    public Cursor getContact(String user_name, SQLiteDatabase sqLiteDatabase){
        String[] projections = {
                Userdata.NewUserInfo.name,
                Userdata.NewUserInfo.reference,
                Userdata.NewUserInfo.type,
                Userdata.NewUserInfo.date,
                Userdata.NewUserInfo.time,
                Userdata.NewUserInfo.location,
                Userdata.NewUserInfo.description,
                };
        String selection = Userdata.NewUserInfo.name+" LIKE ?";
        String [] selection_args = {user_name};
        Cursor cursor = sqLiteDatabase.query(Userdata.NewUserInfo.Table_name,projections,selection,selection_args,null,null,null);
        return cursor;
    }

    public void deleteinfo(String user_name,SQLiteDatabase sqLiteDatabase){
        String selection = Userdata.NewUserInfo.name+" LIKE ?";
        String [] selection_args = {user_name};
        sqLiteDatabase.delete(Userdata.NewUserInfo.Table_name,selection,selection_args);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
