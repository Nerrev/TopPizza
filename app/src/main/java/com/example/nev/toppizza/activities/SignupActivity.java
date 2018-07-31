package com.example.nev.toppizza.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.User;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.SQLhelper;


public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        Button sign = (Button) findViewById(R.id.sign);

        AppCompatSpinner gender = (AppCompatSpinner) findViewById(R.id.gender);
        String[] options = {"Male", "Female"};
        ArrayAdapter objGenderArr = new ArrayAdapter(this, android.R.layout.simple_spinner_item, options);
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
                    AppCompatSpinner gender = (AppCompatSpinner) findViewById(R.id.gender);


                    String fName = "NA";
                    String lName = "NA";
                    String emailt = "NA";
                    String phonet = "NA";
                    String passt = "NA";
                    String conpasst = "NA";
                    String gendert = "NA";

                    fName = firstName.getText().toString();
                    lName = lastName.getText().toString();
                    emailt = email.getText().toString();
                    phonet = phone.getText().toString();
                    passt = pass.getText().toString();
                    conpasst = conpass.getText().toString();
                    gendert = gender.getSelectedItem().toString();

                    User user = new User(emailt, Functions.encrypt(passt), fName, lName, phonet, gendert);

                    if (!Functions.checkText(fName))
                        Toast.makeText(getApplicationContext(), "Error: invalid first name.",
                                Toast.LENGTH_LONG).show();
                    else if (!Functions.checkText(lName))
                        Toast.makeText(getApplicationContext(), "Error: invalid last name.",
                                Toast.LENGTH_LONG).show();
                    else if (!Functions.isValidEmailAddress(emailt))
                        Toast.makeText(getApplicationContext(), "Error: invalid email.",
                                Toast.LENGTH_LONG).show();
                    else if (!Functions.checkNumber(phonet))
                        Toast.makeText(getApplicationContext(), "Error: invalid phone number.",
                                Toast.LENGTH_LONG).show();

                    else if (!Functions.checkPass(passt) || !Functions.checkPassword(passt))
                        Toast.makeText(getApplicationContext(), "Error: invalid Password. (has to have numbers & chars and has to be at least 8 characters long) ",
                                Toast.LENGTH_LONG).show();

                    else if (!passt.equals(conpasst)) { //validate
                        Toast.makeText(getApplicationContext(), "Error: Passwords do not match!",
                                Toast.LENGTH_LONG).show();
                    } else { //sign up

                        SQLhelper d = new SQLhelper(SignupActivity.this);
                        if (!d.insertUser(user))
                            Toast.makeText(getApplicationContext(), "Error: Used Email, please use another one.",
                                    Toast.LENGTH_LONG).show();


                        else {
                            Toast.makeText(getApplicationContext(), "Sign up Successful",
                                    Toast.LENGTH_LONG).show();
                            finish();
                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
