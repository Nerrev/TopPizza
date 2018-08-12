package com.example.nev.toppizza.fragments;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.dd.CircularProgressButton;
import com.example.nev.toppizza.R;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    private static int RESULT_LOAD_IMAGE = 1;
    private Activity activity;
    CircularProgressButton passwordConfirm;
    CircularProgressButton infoConfirm;

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();

        // REMOVE
        SQLhelper dbh = new SQLhelper(activity);
        Login.user=dbh.getUserByEmail("admin2@gmail.com");
        Login.user.moveToNext();

        //REMOVE

        passwordConfirm = activity.findViewById(R.id.Pconfirm);
        infoConfirm = activity.findViewById(R.id.Pedit);
        final TextView email = (TextView) activity.findViewById(R.id.profileEmail);
        final ImageButton profilePic = activity.findViewById(R.id.profilePic);
        final EditText firstName = (EditText) activity.findViewById(R.id.PfirstName);
        final EditText newPass = (EditText) activity.findViewById(R.id.Ppass);
        final EditText oldPass = (EditText) activity.findViewById(R.id.PoldPass);
        final EditText lastName = (EditText) activity.findViewById(R.id.PlastName);
        final EditText phone = (EditText) activity.findViewById(R.id.Pphone);
        final EditText conpass = (EditText) activity.findViewById(R.id.Pconpass);

        byte[] profilePicture=null;
        int index=-1;
         index=Login.user.getColumnIndex("IMAGE");
        if(index!=-1) {
            profilePicture = Login.user.getBlob(index);
            if (profilePicture != null && profilePicture.length > 0)
                profilePic.setImageBitmap(Functions.getImage(profilePicture));
        }
        email.setText(Login.user.getString(Login.user.getColumnIndex("EMAIL")));

        firstName.setText(Login.user.getString(Login.user.getColumnIndex("FNAME")));
        lastName.setText(Login.user.getString(Login.user.getColumnIndex("LNAME")));
        phone.setText(Login.user.getString(Login.user.getColumnIndex("PHONE")));

        infoConfirm.setProgress(0);
        passwordConfirm.setProgress(0);

        infoConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fName = "NA";
                String lName = "NA";
                String phonet = "NA";
                infoConfirm.setProgress(50);
                fName = firstName.getText().toString();
                lName = lastName.getText().toString();
                phonet = phone.getText().toString();


                if (!Functions.checkText(fName)) {
                    infoConfirm.setProgress(-1);

                    Toast.makeText(activity, "Error: invalid first name.",
                            Toast.LENGTH_LONG).show();
                } else if (!Functions.checkText(lName)) {
                    infoConfirm.setProgress(-1);

                    Toast.makeText(activity, "Error: invalid last name.",
                            Toast.LENGTH_LONG).show();

                }else if (!Functions.checkNumber(phonet)) {
                        infoConfirm.setProgress(-1);

                        Toast.makeText(activity, "Error: invalid phone number.",
                                Toast.LENGTH_LONG).show();
                    }

                else {
                    SQLhelper dbh = new SQLhelper(activity);
                   if( !dbh.updateUser(email.getText().toString(),fName,lName,null,phonet,null)) {
                       Toast.makeText(activity, "Something Went Wrong",
                               Toast.LENGTH_LONG).show();
                       infoConfirm.setProgress(-1);
                   }
                   else {
                       infoConfirm.setProgress(100);
                       Login.user=dbh.getUserByEmail(email.getText().toString());
                       resetScene();
                       activity.recreate();
                   }
                }
            }
        });



        passwordConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassw = newPass.getText().toString();;
                String conpasst = conpass.getText().toString();;
                String oldPassword= oldPass.getText().toString();;

                passwordConfirm.setProgress(50);

                if(!Functions.encrypt(oldPassword).equals(Login.user.getString(Login.user.getColumnIndex("PASSWORD")))){
                    Toast.makeText(activity, "Error: Incorrect old password ",
                            Toast.LENGTH_LONG).show();
                }
                else if (!Functions.checkPass(newPassw) || !Functions.checkPassword(newPassw)) {
                    passwordConfirm.setProgress(-1);
                    Toast.makeText(activity, "Error: invalid Password. (must have numbers & chars and has to be at least 8 characters long) ",
                            Toast.LENGTH_LONG).show();

                } else if (!newPassw.equals(conpasst)) { //validate
                    Toast.makeText(activity, "Error: Passwords do not match!",
                            Toast.LENGTH_LONG).show();
                    passwordConfirm.setProgress(-1);

                }
                else{
                    SQLhelper dbh = new SQLhelper(activity);
                    if( !dbh.updateUser(email.getText().toString(),null,null,null,null,newPassw)) {
                        Toast.makeText(activity, "Something Went Wrong",
                                Toast.LENGTH_LONG).show();
                        passwordConfirm.setProgress(-1);
                    }
                    else {
                        passwordConfirm.setProgress(100);
                        Login.user=dbh.getUserByEmail(email.getText().toString());
                        Toast.makeText(activity, "Password Successfully changed.",
                                Toast.LENGTH_LONG).show();
                        resetScene();
                        activity.recreate();

                    }

                }

            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImage);
                if(!Functions.validateImage(bitmap,200)){
                    Toast.makeText(activity, "Picture size is too big.",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    final ImageButton profilePic = activity.findViewById(R.id.profilePic);
                    final TextView email = (TextView) activity.findViewById(R.id.profileEmail);
                    profilePic.setImageBitmap(bitmap);
                    SQLhelper dbh = new SQLhelper(activity);
                    if (!dbh.updateUser(email.getText().toString(), null, null, Functions.getBitmapAsByteArray(bitmap), null, null)) {
                        Toast.makeText(activity, "Something Went Wrong",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Login.user = dbh.getUserByEmail(email.getText().toString());
                        resetScene();
                        activity.recreate();
                        Toast.makeText(activity, "Profile picture changed Successfully.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } catch (FileNotFoundException e) {
                Toast.makeText(activity, "Something Went Wrong. Couldn't change profile Picture",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            } catch (IOException e) {
                Toast.makeText(activity, "Something Went Wrong. Couldn't change profile Picture",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }


    }
    private void resetScene(){

        final EditText newPass = (EditText) activity.findViewById(R.id.Ppass);
        final EditText oldPass = (EditText) activity.findViewById(R.id.PoldPass);
        final EditText conpass = (EditText) activity.findViewById(R.id.Pconpass);
        passwordConfirm = activity.findViewById(R.id.Pconfirm);
        infoConfirm = activity.findViewById(R.id.Pedit);

        newPass.setText("");
        conpass.setText("");
       oldPass.setText("");
       passwordConfirm.setProgress(0);
       infoConfirm.setProgress(0);

    }

}
