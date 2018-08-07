package com.example.nev.toppizza.controllers;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class StartUp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

    }
}
