package com.example.nev.toppizza.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;


import com.example.nev.toppizza.activities.StartActivity;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.PizzaJasonParser;

import java.util.List;


public class ConnectionAsyncTask extends AsyncTask<String, String, String> {

    Activity activity;
    private ProgressDialog dialog;
    public ConnectionAsyncTask(Activity activity) {
        dialog = new ProgressDialog(activity);
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Loading Please Wait ...");
        dialog.show();
        ((StartActivity) activity).setButtonText("connecting");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {

        String data = HttpManager.getData(params[0]);
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        dialog.dismiss();
        ((StartActivity) activity).setButtonText("connected");
        List<Pizza> students = PizzaJasonParser.getObjectFromJason(s);
        ((StartActivity) activity).printPizzas(students);

    }
}
