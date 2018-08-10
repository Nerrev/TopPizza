package com.example.nev.toppizza.services;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;


public class Functions {


    public static boolean checkText(String text) {  //return true if the string contains only chars or numbers or both
        if (!text.matches("[a-zA-Z0-9]+"))
            return false;

        if (text.length() < 3)
            return false;

        return true;

    }

    public static boolean checkText2(String text) {  //return true if the string contains only chars
        if (!text.matches("[a-zA-Z ]+"))
            return false;

        if (text.length() < 3)
            return false;

        return true;

    }

    public static boolean checkPassword(String pass) { //return true if the string contains both chars and  numbers
        int let = 0, num = 0;
        for (int i = 0; i < pass.length(); i++) {
            if (Character.isLetter(pass.charAt(i)))
                let = 1;
            if (Character.isDigit(pass.charAt(i)))
                num = 1;
        }

        if (let == 1 && num == 1)
            return true;

        return false;
    }

    public static boolean checkPass(String text) { //return true if the string contains only chars or numbers or some special chars or both
        if (!text.matches("[a-zA-Z0-9!@#]+"))
            return false;

        if (text.length() < 8)
            return false;

        return true;

    }

    public static boolean checkEmail(String mail) { // validation for emails.

        if (mail.length() < 7)
            return false;

        if (!mail.matches("[a-zA-Z0-9@._-]+"))
            return false;

        if (!mail.contains("@") || !mail.substring(mail.length() - 4).toLowerCase().equals(".com"))
            return false;


        return true;
    }


    public static boolean checkNumber(String text) {  //validation for phone number

        if (text.matches("^05[0-9]{8}+"))
            return true;

        return false;


    }

    public static boolean checkInt(String text) {

        if (text.matches("[0-9]+"))
            return true;

        return false;

    }

    public static boolean checkDoub(String text) {

        try {

            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }

    }


    public static String generatePass() {  //generates a 6 digit random number
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);

        return String.valueOf(n);
    }

    public static void popUp(String m) { //shows an alert with the input message M


    }

    public static int CalculateAge(Date dob) { // calculates Age ,DOB input

        Calendar now = Calendar.getInstance();
        int year1 = now.get(Calendar.YEAR);
        int year2 = dob.getYear();
        int age = year1 - year2;
        return age - 1900;
    }


    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static String encrypt(String p) {
        String s = Base64.encodeToString(MD5_Hash(p).getBytes(), 0);
        Log.d("ENC", s);
        return s;
    }

    public static String encrypt2(String p) {
        String s = Base64.encodeToString(p.getBytes(), 0);
        Log.d("ENC", s);
        return s;
    }

    public static String dec(String p) {
        return new String(Base64.decode(p.getBytes(), 0));
    }


    public static String MD5_Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }


    public static String decrypt(String p) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return md.digest(Base64.decode(p, 0)).toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public AlertDialog popup(Activity activity,String message,String title){


        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        builder.setMessage(message)
                .setTitle(title);
        return builder.create();
    }
    public static boolean isNetworkAvailable(Context context)
    {
        ConnectivityManager connectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static byte[] getBitmapAsByteArray(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        return outputStream.toByteArray();
    }
    public static  Bitmap getImage(byte[] imgByte){
            return BitmapFactory.decodeByteArray(imgByte, 0, imgByte.length);
        }
    public static void delay(long duration) {
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
            }
        };
        h.postDelayed(r, duration);

    }

}
