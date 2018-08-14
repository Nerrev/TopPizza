package com.example.nev.toppizza.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.Order;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrderFragment extends Fragment {

    // TODO: Customize parameters
    private int mColumnCount =1;
    private OnListFragmentInteractionListener mListener;

    final int USER_ORDERS=1;

    public OrderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            List<Order> ordersList=new ArrayList<Order>();


            SQLhelper dbh = new SQLhelper(getActivity());
            Cursor orders;
            int mode=0;
            mode=getArguments().getInt("mode");
            if( mode == USER_ORDERS)
                orders= dbh.getUserOrders(Login.user.getInt(Login.user.getColumnIndex("ID")));
            else
                orders= dbh.getAllOrders();

            while(orders.moveToNext()){
                Order order=new Order();
                order.setPayment(orders.getString(orders.getColumnIndex("PAYMENT")));
                order.setOdate(orders.getString(orders.getColumnIndex("ORDERDATE")));
                Cursor customer = dbh.getUserName(orders.getInt(orders.getColumnIndex("ID")));
                customer.moveToNext();
                order.setCustomer(customer.getString(customer.getColumnIndex("FNAME"))+" "+customer.getString(customer.getColumnIndex("LNAME")));
                Cursor pizza = dbh.getPizzaName(orders.getInt(orders.getColumnIndex("PID")));
                pizza.moveToNext();
                order.setPizzaName(pizza.getString(pizza.getColumnIndex("NAME")));
                ordersList.add(order);
            }



            recyclerView.setAdapter(new MyOrderRecyclerViewAdapter(ordersList, mListener,mode));
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Order order);
    }
}
