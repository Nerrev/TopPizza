package com.example.nev.toppizza.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nev.toppizza.R;

/**
 * A simple {@link Fragment} subclass.
 */
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

}
