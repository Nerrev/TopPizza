package com.example.nev.toppizza.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.nev.toppizza.R;


public class BeginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);


        Button sign = (Button) findViewById(R.id.mainsign);
        Button login = (Button) findViewById(R.id.mainlogin);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BeginActivity.this, SignupActivity.class);
                BeginActivity.this.startActivity(i);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BeginActivity.this, LoginActivity.class);
                BeginActivity.this.startActivity(i);
            }
        });


    }
}
