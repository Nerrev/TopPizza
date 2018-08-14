package com.example.nev.toppizza.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nev.toppizza.R;


public class ContactUsFragment extends Fragment {


    public ContactUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_us, container, false);
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button call= (Button) getActivity().findViewById(R.id.callBtn);
        Button mail= (Button) getActivity().findViewById(R.id.mailBtn);
        Button map= (Button) getActivity().findViewById(R.id.mapBtn);



        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_DIAL, Uri.parse("tel:0599000000"));
                startActivity(myIntent);
            }
        });

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_SENDTO);
                myIntent.setType("message/rfc822");
                myIntent.setData(Uri.parse("mailto:TopPizza@Pizza.com"));
                myIntent.putExtra(Intent.EXTRA_SUBJECT, "my subject");
                myIntent.putExtra(Intent.EXTRA_TEXT, "content of the message");
                startActivity(myIntent);
            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:19.076,72.8777"));
                startActivity(myIntent);
            }
        });





    }

}
