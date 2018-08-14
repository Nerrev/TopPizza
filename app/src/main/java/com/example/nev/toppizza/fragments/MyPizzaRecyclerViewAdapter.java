package com.example.nev.toppizza.fragments;

;
import android.app.Activity;
import android.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nev.toppizza.R;
import com.example.nev.toppizza.fragments.PizzaFragment.OnListFragmentInteractionListener;
import com.example.nev.toppizza.models.Pizza;
import com.example.nev.toppizza.services.Login;
import com.example.nev.toppizza.services.SQLhelper;

import java.util.List;

public class MyPizzaRecyclerViewAdapter extends RecyclerView.Adapter<MyPizzaRecyclerViewAdapter.ViewHolder> {

    private final List<Pizza> mValues;
    private final OnListFragmentInteractionListener mListener;
    Activity activity;
    public MyPizzaRecyclerViewAdapter(List<Pizza> items, OnListFragmentInteractionListener listener, Activity activity1) {
        mValues = items;
        mListener = listener;
        activity=activity1;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pizza, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mItem = mValues.get(position);
       holder.mPizzaType.setText("Type: "+mValues.get(position).getType());
       holder.mPizzaName.setText("Pizza: "+mValues.get(position).getName());
       holder.mPizzaOffer.setText("Offer ? "+mValues.get(position).getOffer());
        final int pid= mValues.get(position).getPid();
        final int index=position;
        final int FID=isFavourited(pid);

        if(FID>0){
            mValues.get(position).setF(true);
            holder.mFavorite.setImageResource(R.drawable.greenhearts2);
        }else{
            mValues.get(position).setF(false);
            holder.mFavorite.setImageResource(R.drawable.greenhearts0);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLhelper dbh= new SQLhelper(activity);

                if(mValues.get(index).isF()){
                    holder.mFavorite.setImageResource(R.drawable.greenhearts0);
                    dbh.deleteFavorite(FID);
                    mValues.get(index).setF(false);
                }

                    else{
                    holder.mFavorite.setImageResource(R.drawable.greenhearts2);
                    dbh.insertFavorite(Login.user.getInt(Login.user.getColumnIndex("ID")), pid);
                    mValues.get(index).setF(true);
                }
                }
        });
        holder.mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = activity.getFragmentManager();
                OrderDialogFragment orderDialog = OrderDialogFragment.newInstance(mValues.get(position));
                orderDialog.show(fm,"ORD");
            }
        });
    }

    public int isFavourited(int pid){
        SQLhelper dbh = new SQLhelper(activity);
        return dbh.checkFav(Login.user.getInt(Login.user.getColumnIndex("ID")),pid);
    }
    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPizzaType;
        public final TextView mPizzaName;
        public final TextView mPizzaOffer;
        public final ImageView mFavorite;
        public final ImageView mOrder;
        public Pizza mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPizzaType = (TextView) view.findViewById(R.id.pizzaType);
            mPizzaName = (TextView) view.findViewById(R.id.pizzaname);
            mPizzaOffer = (TextView) view.findViewById(R.id.pizzaOffer);
            mFavorite= (ImageView) view.findViewById(R.id.favPizzaBtn);
            mOrder= (ImageView) view.findViewById(R.id.orderPizzaBtn);
        }

        @Override
        public String toString() {
            return"";
        }
    }
}
