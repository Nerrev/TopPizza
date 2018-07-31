package com.example.nev.toppizza.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nev.toppizza.models.User;


public class SQLhelper extends SQLiteOpenHelper {

    public SQLhelper(Context context)
    {
        super(context, "PIZZA", null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(ID INTEGER  PRIMARY KEY AUTOINCREMENT,FNAME TEXT,LNAME TEXT,PHONE TEXT,GENDER TEXT, EMAIL TEXT UNIQUE,PASSWORD TEXT,TYPE TEXT)");
        db.execSQL("CREATE TABLE PIZZA(PID INTEGER  PRIMARY KEY AUTOINCREMENT,NAME TEXT,SUMMARY TEXT,TYPE TEXT," +
                "SPRICE REAL,MPRICE REAL,LPRICE REAL, offer BOOLEAN)");
        db.execSQL("CREATE TABLE FAVORITES(FID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
        db.execSQL("CREATE TABLE ORDERS(OID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER, PAYMENT REAL,ORDERDATE DATE,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
    }
    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertUser(User user){
        ContentValues contentValues= new ContentValues();
        contentValues.put("FNAME",user.getFirstName());
        contentValues.put("LNAME",user.getLastName());
        contentValues.put("EMAIL",user.getEmail());
        contentValues.put("PHONE",user.getPhone());
        contentValues.put("PASSWORD",user.getPassword());
        contentValues.put("GENDER",""+user.getGender());
        contentValues.put("TYPE",""+user.getType());


        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insertOrThrow("USER", null,contentValues);
            return true;
        }catch (SQLiteException e){
            e.printStackTrace();

            return false;
        }
    }

    public Cursor getUserById(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from USER where ID="+id,null);

        return cursor;
    }

    public Cursor getUserByEmail(String email){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from USER where EMAIL='"+email+"'",null);

        return cursor;
    }
    public Cursor getAll(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from USER",null);

        return cursor;
    }

    public void DeleteById(String id){
        SQLiteDatabase db = getWritableDatabase();
        //db.rawQuery("DELETE * from CUSTOMER where ID="+id,null);
        db.delete("USER", "ID" + "=" + id, null);
    }

}
