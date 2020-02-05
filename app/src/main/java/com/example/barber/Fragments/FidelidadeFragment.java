package com.example.barber.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.barber.R;

/**
 * A simple {@link androidx.fragment.app.Fragment} subclass.
 */
public class FidelidadeFragment extends Fragment {


    public FidelidadeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fidelidade, container, false);
    }

}
