package com.example.nev.toppizza.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.nev.toppizza.R;


public class UserActivity extends AppCompatActivity {
    public static Cursor userin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        TextView name = (TextView) findViewById(R.id.uname);
        TextView pass = (TextView) findViewById(R.id.upassword);
        TextView email = (TextView) findViewById(R.id.uemail);

        name.setText("name: " + this.userin.getString(this.userin.getColumnIndex("FNAME")));
        pass.setText("Password: " + this.userin.getString(this.userin.getColumnIndex("PASSWORD")));
        email.setText("Email: " + this.userin.getString(this.userin.getColumnIndex("EMAIL")));


        Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userin = null;

                Intent i = new Intent(UserActivity.this, BeginActivity.class);
                UserActivity.this.startActivity(i);
                finish();

            }
        });

    }
}
