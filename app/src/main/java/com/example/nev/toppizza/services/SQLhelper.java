package com.example.nev.toppizza.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.models.User;

import java.util.List;


public class SQLhelper extends SQLiteOpenHelper {

    public SQLhelper(Context context)
    {
        super(context, "PIZZA", null, 1);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(ID INTEGER  PRIMARY KEY AUTOINCREMENT,FNAME TEXT,LNAME TEXT,PHONE TEXT,GENDER TEXT, EMAIL TEXT UNIQUE,PASSWORD TEXT,TYPE TEXT, IMAGE BLOB)");
        db.execSQL("CREATE TABLE PIZZA(PID INTEGER  PRIMARY KEY AUTOINCREMENT,NAME TEXT,SUMMARY TEXT,TYPE TEXT,SPRICE TEXT,MPRICE TEXT,LPRICE TEXT,OFFER TEXT)");
        db.execSQL("CREATE TABLE FAVORITES(FID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
        db.execSQL("CREATE TABLE ORDERS(OID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER, PAYMENT TEXT,ORDERDATE DATE,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
        db.execSQL("CREATE TABLE Advert(AID INTEGER  PRIMARY KEY AUTOINCREMENT, AD BLOB,START_DATE DATE,END_DATE DATE )");
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
    public Cursor getPizzaById(String id){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from PIZZA where PID="+id,null);

        return cursor;
    }

    public Cursor getAllPizza(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor=db.rawQuery("select * from PIZZA",null);

        return cursor;
    }

    public void updatePizzaTable(List<Pizza> pizza){
        SQLiteDatabase db = getWritableDatabase();
        int i=0;
        for(i=0;i<pizza.size();i++) {
            ContentValues contentValues= new ContentValues();
            contentValues.put("NAME",pizza.get(i).getName());
            contentValues.put("SUMMARY",pizza.get(i).getSummary());
            contentValues.put("TYPE",pizza.get(i).getType());
            contentValues.put("SPRICE",pizza.get(i).getPrice()[0]);
            contentValues.put("MPRICE",pizza.get(i).getPrice()[1]);
            contentValues.put("LPRICE",pizza.get(i).getPrice()[2]);
            contentValues.put("offer",pizza.get(i).getOffer());

            if(getPizzaById(""+(i+1)).moveToFirst())
                db.update("PIZZA", contentValues, "PID='" + (i+1)+"'", null);
            else
                db.insert("PIZZA",null,contentValues);

        }
    }
    public void insertPizza(Pizza pizza){

        SQLiteDatabase db = getWritableDatabase();

            ContentValues contentValues= new ContentValues();
            contentValues.put("NAME",pizza.getName());
            contentValues.put("TYPE",pizza.getType());
            contentValues.put("SPRICE",pizza.getPrice()[0]);
            contentValues.put("MPRICE",pizza.getPrice()[1]);
            contentValues.put("LPRICE",pizza.getPrice()[2]);
            contentValues.put("offer",""+pizza.getOffer());

            db.insert("PIZZA",null,contentValues);
    }

}
