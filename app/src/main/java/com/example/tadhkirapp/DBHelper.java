package com.example.tadhkirapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Login.db";

    //Create Login Database
    public DBHelper(Context context) {
        super(context, "Login.db", null, 1);
    }

    //Create Table username which username as the primary key
    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table Users(username TEXT primary key,password TEXT)");
    }

    //Delete table if there are exist users
    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        MyDB.execSQL("drop Table if exists users");
    }

    //Insert Data inside SQLIte Database
    public Boolean insertData (String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = MyDB.insert("users", null,contentValues);
        if(result ==-1 ) return false;
        else
            return true;
    }
    //Checking username
    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username =?", new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //Checking username and password
    public Boolean checkusernamepassword(String username,String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

}

