package com.example.nev.toppizza.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;

import com.example.nev.toppizza.activities.StartActivity;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.PizzaJasonParser;

import java.util.List;


public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    Activity activity;
    private ProgressDialog dialog;
    boolean connected=true;

    public ConnectionAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {

        ((StartActivity) activity).setButtonProg(50);
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String data=null;
        try
        {
            if (Functions.isNetworkAvailable(activity))
                data = HttpManager.getData(params[0]);
            else
                connected=false;
        }
        catch (Exception ex)
        {
            connected=false;
        }

        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        if (connected && s != null) {
            ((StartActivity) activity).setButtonProg(100);
            List<Pizza> pizza = PizzaJasonParser.getObjectFromJason(s);
            ((StartActivity) activity).loadPizza(pizza);
            ((StartActivity) activity).connected();
        } else {
            ((StartActivity) activity).setButtonProg(-1);
            ((StartActivity) activity).connectionError();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    ((StartActivity) activity).setButtonProg(0);

                }
            }, 2000);
        }

    }
}
