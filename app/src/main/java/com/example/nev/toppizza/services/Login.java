package com.example.nev.toppizza.services;

import android.database.Cursor;

import com.example.nev.toppizza.models.User;

public class Login {
    public static User user;

    public static void Login(Cursor cursor){
        Login.user=new User(cursor.getString(cursor.getColumnIndex("EMAIL")),cursor.getString(cursor.getColumnIndex("PASSWORD"))
        ,cursor.getString(cursor.getColumnIndex("FIRSTNAME")),cursor.getString(cursor.getColumnIndex("LASTNAME")),
                cursor.getString(cursor.getColumnIndex("PHONE")),cursor.getString(cursor.getColumnIndex("GENDER")));
    }

    public static void  Logout(){
        user=null;
    }

}
