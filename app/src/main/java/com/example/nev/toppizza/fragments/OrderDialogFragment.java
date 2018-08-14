package com.example.nev.toppizza.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.Order;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.Date;


public class OrderDialogFragment extends DialogFragment {


    public OrderDialogFragment() {
        // Required empty public constructor
    }


    private Pizza pizza;


    public void setPizza(Pizza pizza){
        this.pizza=pizza;
    }

    public static OrderDialogFragment newInstance(Pizza pizza) {

        OrderDialogFragment frag = new OrderDialogFragment();
        frag.setPizza(pizza);
        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_order_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

       final  Spinner oSize = (Spinner) view.findViewById(R.id.Osize);
        String[] options = {"Small", "Medium", "Large"};
        ArrayAdapter objGenderArr = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, options);
        oSize.setAdapter(objGenderArr);


       final  TextView oPrice = (TextView) view.findViewById(R.id.Oprice);


        oSize.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oPrice.setText(pizza.getPrice()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        Button diaOrder = (Button) view.findViewById(R.id.diaOrder);
        Button diaCancel = (Button) view.findViewById(R.id.diaCancel);

        diaCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dismiss(); }});

        diaOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLhelper dbh= new SQLhelper(getActivity());
                Date date=new Date();
                String d=date.toString();
                Order order=new Order(Login.user.getInt(Login.user.getColumnIndex("ID")),pizza.getPid(),oPrice.getText().toString(),date);
                order.setOdate(d);
                dbh.insertOrder(order);
                Toast.makeText(getActivity(), "Order Complete.",
                        Toast.LENGTH_LONG).show();
                dismiss();
            }
        });




    }


}
