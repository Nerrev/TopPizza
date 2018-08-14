package com.example.nev.toppizza.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.Pizza;

import org.w3c.dom.Text;


public class PizzaDialogFragment extends DialogFragment{

    public PizzaDialogFragment() {
        // Required empty public constructor
    }



 private Pizza pizza;


public void setPizza(Pizza pizza){
    this.pizza=pizza;
}

    public static PizzaDialogFragment newInstance(Pizza pizza) {

        PizzaDialogFragment frag = new PizzaDialogFragment();
        frag.setPizza(pizza);
        return frag;

    }



    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,

                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_pizza_dialog, container);

    }



    @Override

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);


        TextView pname = (TextView) view.findViewById(R.id.PzName);
        TextView psummery = (TextView) view.findViewById(R.id.PzSummary);

        pname.setText(pizza.getName());
        psummery.setText(pizza.getSummary());


        Button diaOrder = (Button) view.findViewById(R.id.diaOrder);
        Button diaCancel = (Button) view.findViewById(R.id.diaCancel);

        diaCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dismiss(); }});

        diaOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
                OrderDialogFragment orderDialog = OrderDialogFragment.newInstance(pizza);
               orderDialog.show(fm,"ORD");
                dismiss();
            }
        });




    }

}

