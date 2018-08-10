package com.example.nev.toppizza.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.nev.toppizza.R;
import com.example.nev.toppizza.activities.SignupActivity;
import com.example.nev.toppizza.models.User;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.SQLhelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdmin extends Fragment {


    public AddAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_admin, container, false);
    }
    private CircularProgressButton sign;
    private Activity activity;
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         activity=getActivity();
        sign = (CircularProgressButton) activity.findViewById(R.id.asign);
        sign.setIndeterminateProgressMode(true);
        Spinner gender = (Spinner) activity.findViewById(R.id.agender);
        String[] options = {"Gender", "Male", "Female"};
        ArrayAdapter objGenderArr = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, options);

        gender.setAdapter(objGenderArr);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                try {

                    EditText firstName = (EditText) activity.findViewById(R.id.afirstName);
                    EditText pass = (EditText) activity.findViewById(R.id.apass);
                    EditText lastName = (EditText) activity.findViewById(R.id.alastName);
                    EditText email = (EditText) activity.findViewById(R.id.aemail);
                    EditText phone = (EditText) activity.findViewById(R.id.aphone);
                    EditText conpass = (EditText) activity.findViewById(R.id.aconpass);
                    Spinner gender = (Spinner) activity.findViewById(R.id.agender);


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
                        Toast.makeText(activity, "Error: Fill All Fields.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    gendert = gender.getSelectedItem().toString();

                    User user = new User(emailt, Functions.encrypt(passt), fName, lName, phonet, gendert);

                    if (!Functions.checkText(fName)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(activity, "Error: invalid first name.",
                                Toast.LENGTH_LONG).show();
                    } else if (!Functions.checkText(lName)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(activity, "Error: invalid last name.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.isValidEmailAddress(emailt)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(activity, "Error: invalid email.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.checkNumber(phonet)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(activity, "Error: invalid phone number.",
                                Toast.LENGTH_LONG).show();

                    } else if (!Functions.checkPass(passt) || !Functions.checkPassword(passt)) {
                        sign.setProgress(-1);
                        delay();
                        Toast.makeText(activity, "Error: invalid Password. (must have numbers & chars and has to be at least 8 characters long) ",
                                Toast.LENGTH_LONG).show();

                    } else if (!passt.equals(conpasst)) { //validate
                        Toast.makeText(activity, "Error: Passwords do not match!",
                                Toast.LENGTH_LONG).show();
                        sign.setProgress(-1);
                        delay();

                    } else { //sign up

                        SQLhelper d = new SQLhelper(activity);
                        user.setType("Admin");
                        if (!d.insertUser(user)) {
                            Toast.makeText(activity, "Error: Used Email, please use another one.",
                                    Toast.LENGTH_LONG).show();
                            sign.setProgress(-1);
                            delay();
                        }

                        else {
                            sign.setProgress(100);
                            Toast.makeText(activity, "Admin "+user.getFirstName()+" Added.",
                                    Toast.LENGTH_LONG).show();
                            Handler h = new Handler();
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    resetScene();
                                }
                            };
                            h.postDelayed(r, 1500);


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
    private void resetScene(){
        EditText firstName = (EditText) activity.findViewById(R.id.afirstName);
        EditText pass = (EditText) activity.findViewById(R.id.apass);
        EditText lastName = (EditText) activity.findViewById(R.id.alastName);
        EditText email = (EditText) activity.findViewById(R.id.aemail);
        EditText phone = (EditText) activity.findViewById(R.id.aphone);
        EditText conpass = (EditText) activity.findViewById(R.id.aconpass);
        Spinner gender = (Spinner) activity.findViewById(R.id.agender);

        firstName.setText("");
        pass.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");
        conpass.setText("");
        gender.setSelection(0);
        sign.setProgress(0);
    }
}
