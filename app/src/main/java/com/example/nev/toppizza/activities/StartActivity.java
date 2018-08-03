package com.example.nev.toppizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.controllers.ConnectionAsyncTask;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.List;


public class StartActivity extends AppCompatActivity {
    Button button;
    LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        button = (Button)findViewById(R.id.startBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionAsyncTask connectionAsyncTask= new ConnectionAsyncTask(StartActivity.this);
                connectionAsyncTask.execute("http://www.mocky.io/v2/5b522fa32e000074005c1c40");
            }
        });

        ly= findViewById(R.id.layout1);
    }

    public void loadPizza(List<Pizza> pizza){
        SQLhelper dbh= new SQLhelper(StartActivity.this);
        dbh.updatePizzaTable(pizza);
    }
    public void connected(){
        SQLhelper dbh= new SQLhelper(StartActivity.this);
        if(dbh.getAllPizza().moveToFirst()) {
            Intent i = new Intent(StartActivity.this, BeginActivity.class);
            startActivity(i);
            finish();
        }
    }
    public void setButtonText(String text)
    {
        button.setText(text);
    }

    public void printPizzas()
    {
        LinearLayout linearLayout  = (LinearLayout) findViewById(R.id.layout1);
        linearLayout.removeAllViews();
        SQLhelper dbh= new SQLhelper(StartActivity.this);

            TextView textView = new TextView(this);
            textView.setText(dbh.getPizzaById("1").getString(1).toString());
            linearLayout.addView(textView);

    }
}
