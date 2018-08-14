package com.example.nev.toppizza.fragments;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.OrderFragment.OnListFragmentInteractionListener;
import com.example.nev.toppizza.models.Order;

import java.util.List;

public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final List<Order> mValues;
    private final OnListFragmentInteractionListener mListener;
    final int USER_ORDERS=1;
    int mode=0;
    public MyOrderRecyclerViewAdapter(List<Order> items, OnListFragmentInteractionListener listener,int mode) {
        mValues = items;
        mListener = listener;
        this.mode=mode;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_order, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mOrder = mValues.get(position);
        holder.payment.setText(mValues.get(position).getPayment());
        holder.orderDate.setText(mValues.get(position).getOdate());
        if(mode == USER_ORDERS)
        holder.customer.setText(mValues.get(position).getPizzaName());
        else
            holder.customer.setText(mValues.get(position).getCustomer());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mOrder);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mValues.isEmpty())
            return 0;
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView orderDate;
        public final TextView payment;
        public final TextView customer;

        public Order mOrder;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            orderDate = (TextView) view.findViewById(R.id.orderDate);
            payment= (TextView) view.findViewById(R.id.orderPayment);
            customer= (TextView) view.findViewById(R.id.orderCustomer);
        }

        @Override
        public String toString() {
            return super.toString() + "";
        }
    }
}
