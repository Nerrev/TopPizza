package com.example.nev.toppizza.fragments;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.services.SQLhelper;


public class DelUserFragment extends Fragment {


    public DelUserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_del_user, container, false);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button delCustomerButton= getActivity().findViewById(R.id.delBtn);
        final EditText accEmail=getActivity().findViewById(R.id.delEmail);
        delCustomerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SQLhelper dbh= new SQLhelper(getActivity());
                        if(dbh.deleteUser(accEmail.getText().toString())) {
                            Toast.makeText(getActivity(), "Account Deleted.",
                                    Toast.LENGTH_LONG).show();
                            accEmail.setText("Done");
                        }
                        else
                            Toast.makeText(getActivity(), "Couldn't Find customer!",
                                    Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });

                builder.setMessage("Are you sure you want to delete this account?")
                        .setTitle("Delete Customer");
                AlertDialog pop=builder.create();

                pop.show();

            }
        });

    }

}
