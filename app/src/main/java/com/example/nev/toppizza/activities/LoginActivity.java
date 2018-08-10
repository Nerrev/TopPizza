package com.example.nev.toppizza.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;


public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button sign = (Button) findViewById(R.id.signUp);

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                LoginActivity.this.startActivity(i);

            }
        });


        Button login = (Button) findViewById(R.id.login);
        EditText email1 = (EditText) findViewById(R.id.loginEmail);
        EditText pass1 = (EditText) findViewById(R.id.loginPass);


        SharedPreferences sp = getSharedPreferences("Login Data", Context.MODE_PRIVATE);
        String nt = sp.getString("email", "noValue");
        String pt = sp.getString("password", "noValue");
        if (!sp.getAll().isEmpty()) {
            email1.setText(nt);
            pass1.setText(pt);
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText email = (EditText) findViewById(R.id.loginEmail);
                EditText pass = (EditText) findViewById(R.id.loginPass);


                String emailt = email.getText().toString();
                String passt = pass.getText().toString();
                SQLhelper dbHelper = new SQLhelper(LoginActivity.this);

                Cursor cursor = dbHelper.getUserByEmail(emailt);
                if (!Functions.checkEmail(emailt) || !Functions.checkPass(passt)) {

                    Toast.makeText(getApplicationContext(), "Wrong email or password",
                            Toast.LENGTH_LONG).show();
                } else {



                    if (!cursor.moveToFirst()) {
                        Toast.makeText(getApplicationContext(), "Wrong email or password",
                                Toast.LENGTH_LONG).show();
                    } else if (!cursor.getString(cursor.getColumnIndex("PASSWORD")).equals(Functions.encrypt(passt))) {
                        Toast.makeText(getApplicationContext(), "Wrong email or password",
                                Toast.LENGTH_LONG).show();

                    } else {
                        Login.Login(cursor);
                        Toast.makeText(getApplicationContext(), "Welcome",
                                Toast.LENGTH_LONG).show();


                        CheckBox rem = (CheckBox) findViewById(R.id.remember);
                        SharedPreferences sp = getSharedPreferences("Login Data", Context.MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();


                        if (rem.isChecked()) {
                            ed.putString("email", emailt);
                            ed.putString("password", passt);

                            ed.commit();
                        } else {
                            ed.clear();
                            ed.commit();
                        }

                        if(cursor.getString(cursor.getColumnIndex("TYPE")).equals("Admin")){
                            Intent i = new Intent(LoginActivity.this, AdminActivity.class); // change activity
                            LoginActivity.this.startActivity(i);


                        }
                        else if(cursor.getString(cursor.getColumnIndex("TYPE")).equals("User")) {
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            LoginActivity.this.startActivity(i);
                        }
                    }
                }


            }
        });


    }
}
