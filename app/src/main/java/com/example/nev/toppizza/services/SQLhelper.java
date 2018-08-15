package com.example.nev.toppizza.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nev.toppizza.models.Order;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.models.User;

import java.util.Date;
import java.util.List;


public class SQLhelper extends SQLiteOpenHelper {

    public SQLhelper(Context context) {
        super(context, "PIZZA", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE USER(ID INTEGER  PRIMARY KEY AUTOINCREMENT,FNAME TEXT,LNAME TEXT,PHONE TEXT,GENDER TEXT, EMAIL TEXT UNIQUE,PASSWORD TEXT,TYPE TEXT, IMAGE BLOB)");
        db.execSQL("CREATE TABLE PIZZA(PID INTEGER  PRIMARY KEY AUTOINCREMENT,NAME TEXT,SUMMARY TEXT,TYPE TEXT,SPRICE TEXT,MPRICE TEXT,LPRICE TEXT,OFFER TEXT)");
        db.execSQL("CREATE TABLE FAVORITES(FID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
        db.execSQL("CREATE TABLE ORDERS(OID INTEGER  PRIMARY KEY AUTOINCREMENT,ID INTEGER,PID INTEGER, PAYMENT TEXT,ORDERDATE TEXT,FOREIGN KEY(ID) REFERENCES USER(ID),FOREIGN KEY(PID) REFERENCES PIZZA(PID))");
        db.execSQL("CREATE TABLE Advert(AID INTEGER  PRIMARY KEY AUTOINCREMENT, AD BLOB,START_DATE TEXT,END_DATE TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("FNAME", user.getFirstName());
        contentValues.put("LNAME", user.getLastName());
        contentValues.put("EMAIL", user.getEmail());
        contentValues.put("PHONE", user.getPhone());
        contentValues.put("PASSWORD", user.getPassword());
        contentValues.put("GENDER", "" + user.getGender());
        contentValues.put("TYPE", "" + user.getType());


        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insertOrThrow("USER", null, contentValues);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();

            return false;
        }
    }
    public boolean insertAdvert(Date start, Date end, byte[] img){
        ContentValues contentValues = new ContentValues();
        contentValues.put("START_DATE", start.toString());
        contentValues.put("END_DATE", end.toString());
        contentValues.put("AD", img);


        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insertOrThrow("ADVERT", null, contentValues);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();

            return false;
        }
    }

    public boolean updateUser(String email,String firstName, String lastName, byte[] profilePic, String phone, String passwrod){

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor=getUserByEmail(email);
        if (!cursor.moveToFirst())
            return false;

        ContentValues contentValues = new ContentValues();
        if(firstName!=null)
            contentValues.put("FNAME",firstName);
        if(lastName!=null)
            contentValues.put("LNAME",lastName);
        if(phone!=null)
            contentValues.put("PHONE",phone);
        if( profilePic!=null)
            contentValues.put("IMAGE",profilePic);
        if(passwrod!=null)
            contentValues.put("PASSWORD",passwrod);

        try {
            db.updateWithOnConflict("USER", contentValues,"EMAIL='"+email+"'" , null,SQLiteDatabase.CONFLICT_ABORT);
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteUser(String email) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor=getUserByEmail(email);
        if (!cursor.moveToFirst())
            return false;

        String mail = cursor.getString(cursor.getColumnIndex("EMAIL"));
        ContentValues contentValues = new ContentValues();
        contentValues.put("EMAIL",mail+"#"+cursor.getString(cursor.getColumnIndex("ID")));
        contentValues.put("TYPE", "DELETED");

            try {
                db.updateWithOnConflict("USER", contentValues,"EMAIL='"+mail+"'" , null,SQLiteDatabase.CONFLICT_ABORT);
            }
            catch (Exception e){
                e.printStackTrace();
                return false;
            }
        return true;
    }


    public Cursor getUserByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USER where EMAIL='" + email + "'", null);

        return cursor;
    }
    public Cursor getUserName(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select FNAME,LNAME from USER where ID=" + id + "", null);

        return cursor;
    }

    public Cursor getOffers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA where OFFER='" + "yes"+ "'", null);
        return cursor;
    }

    public Cursor getPizzaName(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select NAME from PIZZA where PID=" + id + "", null);

        return cursor;
    }
    public Cursor getPizzaByType(String type){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA where TYPE='" + type + "'", null);

        return cursor;
    }
    public Cursor getPizzaByPrice(int size,String price){
        SQLiteDatabase db = getReadableDatabase();
        String p = price+"$";
        Cursor cursor;
        if(size == 0)
            cursor = db.rawQuery("select * from PIZZA where SPRICE LIKE'" + p + "'", null);
        else if(size == 1)
             cursor = db.rawQuery("select * from PIZZA where MPRICE LIKE'" + p + "'", null);
        else
             cursor = db.rawQuery("select * from PIZZA where LPRICE LIKE'" + p + "'", null);

        return cursor;
    }
    public Cursor getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from USER", null);
        return cursor;
    }
    public Cursor getTypes(){

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select TYPE from PIZZA", null);

        return cursor;

    }
    public void removeOrders(){

        SQLiteDatabase db = getWritableDatabase();
        db.delete("ORDERS","1=1",null);
    }
    public boolean insertFavorite(int user, int pizza) {

        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", user);
        contentValues.put("PID", pizza);


        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insertOrThrow("FAVORITES", null, contentValues);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();

            return false;
        }
    }

    public Cursor getUserFavorites(int user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA, FAVORITES WHERE PIZZA.PID=FAVORITES.PID and FAVORITES.ID=" + user, null);

        return cursor;

    }

    public int checkFav(int user,int pizza){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select FID from FAVORITES WHERE ID="+user+ " and PID=" + pizza, null);

        if(cursor.moveToFirst())
            return cursor.getInt(cursor.getColumnIndex("FID"));

        return  -1;
    }
    public void deleteFavorite(int FID){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("FAVORITES","FID="+FID,null);
    }
    public Cursor getAllOrders() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ORDERS", null);

        return cursor;

    }


    public boolean insertOrder(Order order) {


        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", order.getUser());
        contentValues.put("PID", order.getPizza());
        contentValues.put("PAYMENT", order.getPayment());
        contentValues.put("ORDERDATE", order.getDate().toString());


        try {
            SQLiteDatabase db = getWritableDatabase();
            db.insertOrThrow("ORDERS", null, contentValues);
            return true;
        } catch (SQLiteException e) {
            e.printStackTrace();

            return false;
        }
    }

    public Cursor getUserOrders(int user) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA as P , ORDERS as O WHERE P.PID=O.PID and O.ID=" + user, null);

        return cursor;

    }

    public Cursor getOrdersByType(String type) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ORDERS as O , PIZZA as P WHERE O.PID=P.PID and P.TYPE='" +type+"'", null);
        return cursor;

    }


    public Cursor getPizzaById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA where PID=" + id, null);

        return cursor;
    }

    public Cursor getAllPizza() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from PIZZA", null);

        return cursor;
    }

    public void updatePizzaTable(List<Pizza> pizza) {
        SQLiteDatabase db = getWritableDatabase();
        int i = 0;
        for (i = 0; i < pizza.size(); i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("NAME", pizza.get(i).getName());
            contentValues.put("SUMMARY", pizza.get(i).getSummary());
            contentValues.put("TYPE", pizza.get(i).getType());
            contentValues.put("SPRICE", pizza.get(i).getPrice()[0]);
            contentValues.put("MPRICE", pizza.get(i).getPrice()[1]);
            contentValues.put("LPRICE", pizza.get(i).getPrice()[2]);
            contentValues.put("offer", pizza.get(i).getOffer());

            if (getPizzaById(i + 1).moveToFirst())
                db.update("PIZZA", contentValues, "PID=" + (i + 1), null);
            else
                db.insert("PIZZA", null, contentValues);

        }
    }

    public void insertPizza(Pizza pizza) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME", pizza.getName());
        contentValues.put("TYPE", pizza.getType());
        contentValues.put("SPRICE", pizza.getPrice()[0]);
        contentValues.put("MPRICE", pizza.getPrice()[1]);
        contentValues.put("LPRICE", pizza.getPrice()[2]);
        contentValues.put("offer", "" + pizza.getOffer());

        db.insert("PIZZA", null, contentValues);
    }

}
