package com.example.nev.toppizza.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;


import java.util.ArrayList;
import java.util.List;


public class PizzaFragment extends Fragment {

    private int mColumnCount = 1;
    final int FAVORITE_MODE=1;
    final int OFFERS_MODE=2;
    final int FILTER_TYPE_MODE=3;
    final int FILTER_PRICE_MODE=4;
    private OnListFragmentInteractionListener mListener;

    public PizzaFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            List<Pizza> pizzaList = new ArrayList<>();


            SQLhelper dbh = new SQLhelper(getActivity());
            Cursor pizzas;
            int mode=0;
            mode=getArguments().getInt("mode");
            if( mode == FAVORITE_MODE)
                pizzas= dbh.getUserFavorites(Login.user.getInt(Login.user.getColumnIndex("ID")));
            else if(mode == OFFERS_MODE)
                pizzas= dbh.getOffers() ;
            else if(mode == FILTER_TYPE_MODE)
                pizzas= dbh.getPizzaByType(getArguments().getString("Type")) ;
            else if(mode == FILTER_PRICE_MODE)
                pizzas= dbh.getPizzaByPrice(getArguments().getInt("size"),getArguments().getString("Price")) ;
            else
                pizzas= dbh.getAllPizza() ;

            while(pizzas.moveToNext()){
                String name;
                String summary;
                String type;
                String[] price = new String[3];
                String offer;

              name=pizzas.getString(pizzas.getColumnIndex("NAME"));
              summary=pizzas.getString(pizzas.getColumnIndex("SUMMARY"));
              type=pizzas.getString(pizzas.getColumnIndex("TYPE"));
              offer=pizzas.getString(pizzas.getColumnIndex("OFFER"));
                price[0]=pizzas.getString(pizzas.getColumnIndex("SPRICE"));
                price[1]=pizzas.getString(pizzas.getColumnIndex("MPRICE"));
                price[2]=pizzas.getString(pizzas.getColumnIndex("LPRICE"));



                Pizza pizza=new Pizza(name,summary,type,price,offer);
                pizza.setPid(pizzas.getInt(pizzas.getColumnIndex("PID")));
                pizzaList.add(pizza);
            }




            recyclerView.setAdapter(new MyPizzaRecyclerViewAdapter(pizzaList, mListener,getActivity(),mode));
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
        void onListFragmentInteraction(Pizza pizza);
    }
}
