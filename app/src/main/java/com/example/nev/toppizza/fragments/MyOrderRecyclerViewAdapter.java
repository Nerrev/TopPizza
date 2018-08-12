package com.example.nev.toppizza.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.OrderFragment.OnListFragmentInteractionListener;
import com.example.nev.toppizza.fragments.dummy.DummyContent.DummyItem;
import com.example.nev.toppizza.models.Order;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyOrderRecyclerViewAdapter extends RecyclerView.Adapter<MyOrderRecyclerViewAdapter.ViewHolder> {

    private final List<Order> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyOrderRecyclerViewAdapter(List<Order> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
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
        holder.orderDate.setText(mValues.get(position).getDate().toString());
        holder.payment.setText("100");
        holder.customer.setText("PIZZA");

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
            orderDate = (TextView) view.findViewById(R.id.OrderDate);
            payment= (TextView) view.findViewById(R.id.orderPayment);
            customer= (TextView) view.findViewById(R.id.orderCustomer);
        }

        @Override
        public String toString() {
            return super.toString() + "";
        }
    }
}
