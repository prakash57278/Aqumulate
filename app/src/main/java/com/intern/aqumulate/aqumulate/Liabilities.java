package com.intern.aqumulate.aqumulate;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Liabilities extends Fragment {


    public Liabilities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View layout= inflater.inflate(R.layout.fragment_liabilities, container, false);
        return layout;
    }

}
