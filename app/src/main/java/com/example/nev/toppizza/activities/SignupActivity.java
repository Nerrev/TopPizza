package com.example.nev.toppizza.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.User;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.SQLhelper;


public class SignupActivity extends AppCompatActivity {
    CircularProgressButton sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        sign = (CircularProgressButton) findViewById(R.id.sign);
        sign.setIndeterminateProgressMode(true);
        Spinner gender = (Spinner) findViewById(R.id.gender);
        String[] options = {"Gender", "Male", "Female"};
        ArrayAdapter objGenderArr = new ArrayAdapter<String>(this, R.layout.spinner_item, options);

        gender.setAdapter(objGenderArr);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    EditText firstName = (EditText) findViewById(R.id.firstName);

                    EditText lastName = (EditText) findViewById(R.id.lastName);
                    EditText email = (EditText) findViewById(R.id.email);
                    EditText phone = (EditText) findViewById(R.id.phone);
                    EditText pass = (EditText) findViewById(R.id.pass);
                    EditText conpass = (EditText) findViewById(R.id.conpass);
                    Spinner gender = (Spinner) findViewById(R.id.gender);


                    String fName = "NA";
                    String lName = "NA";
                    String emailt = "NA";
                    String phonet = "NA";
                    String passt = "NA";
                    String conpasst = "NA";
                    String gendert = "NA";
                    sign.setProgress(50);
                    fName = firstName.getText().toString();
                    lName = lastName.getText().toString();
                    emailt = email.getText().toString();
                    phonet = phone.getText().toString();
                    passt = pass.getText().toString();
                    conpasst = conpass.getText().toString();
                    if (gender.getSelectedItemPosition() == 0) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: Fill All Fields.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    gendert = gender.getSelectedItem().toString();

                    User user = new User(emailt, Functions.encrypt(passt), fName, lName, phonet, gendert);

                    if (!Functions.checkText(fName)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: invalid first name.",
                                Toast.LENGTH_LONG).show();
                    } else if (!Functions.checkText(lName)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: invalid last name.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.isValidEmailAddress(emailt)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: invalid email.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.checkNumber(phonet)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: invalid phone number.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.checkPass(passt) || !Functions.checkPassword(passt)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(getApplicationContext(), "Error: invalid Password. (must have numbers & chars and has to be at least 8 characters long) ",
                                Toast.LENGTH_LONG).show();

                    } else if (!passt.equals(conpasst)) { //validate
                        Toast.makeText(getApplicationContext(), "Error: Passwords do not match!",
                                Toast.LENGTH_LONG).show();
                        sign.setProgress(-1);
                        delay();

                    } else { //sign up

                        SQLhelper d = new SQLhelper(SignupActivity.this);
                        if (!d.insertUser(user))
                            Toast.makeText(getApplicationContext(), "Error: Used Email, please use another one.",
                                    Toast.LENGTH_LONG).show();


                        else {
                            sign.setProgress(100);
                            Toast.makeText(getApplicationContext(), "Sign up Successful",
                                    Toast.LENGTH_LONG).show();
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            };
                            h.postDelayed(r, 1000);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void delay() {
        Handler h = new Handler();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                sign.setProgress(0);
            }
        };
        h.postDelayed(r, 1500);

    }

}
