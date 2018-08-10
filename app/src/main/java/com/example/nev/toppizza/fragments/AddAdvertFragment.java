package com.example.nev.toppizza.fragments;


import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.dd.CircularProgressButton;
import com.example.nev.toppizza.R;
import com.example.nev.toppizza.services.Functions;
import com.example.nev.toppizza.services.SQLhelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddAdvertFragment extends Fragment {

    private static int RESULT_LOAD_IMAGE = 1;
    public AddAdvertFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_advert, container, false);
    }
    private Activity activity;
    CircularProgressButton submit;
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activity=getActivity();
        DatePicker datePickerStart =  activity.findViewById(R.id.offerStart);

        datePickerStart.setMinDate(new Date().getTime());

       submit= (CircularProgressButton) activity.findViewById(R.id.submitAdBtn);
        submit.setIndeterminateProgressMode(true);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setProgress(50);
                DatePicker datePickerStart =  activity.findViewById(R.id.offerStart);
                DatePicker datePickerEnd=  activity.findViewById(R.id.offerEnd);
                Date endDate = new Date(datePickerEnd.getDayOfMonth(),datePickerEnd.getMonth(),datePickerEnd.getYear());
                Date StartDate = new Date(datePickerStart.getDayOfMonth(),datePickerStart.getMonth(),datePickerStart.getYear());
                if(bitmap!=null) {
                    byte[] data = Functions.getBitmapAsByteArray(bitmap);
                    SQLhelper dbh= new SQLhelper(activity);
                    dbh.insertAdvert(StartDate,endDate,data);
                    Toast.makeText(activity, "Advert Inserted",
                            Toast.LENGTH_LONG).show();
                    submit.setProgress(100);
                }
                else{
                    submit.setProgress(-1);
                    Toast.makeText(activity, "Error: Couldn't insert the advert",
                            Toast.LENGTH_LONG).show();
                }

            }
        });

        ImageButton buttonLoadImage = (ImageButton) activity.findViewById(R.id.advertImage);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), RESULT_LOAD_IMAGE);
            }
        });

    }
    Bitmap bitmap = null;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Detects request codes
        if(requestCode==RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), selectedImage);
                ImageButton buttonLoadImage = (ImageButton) activity.findViewById(R.id.advertImage);
                buttonLoadImage.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }


    }


}
