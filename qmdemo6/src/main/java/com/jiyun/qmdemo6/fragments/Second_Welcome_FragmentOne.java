package com.jiyun.qmdemo6.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.qmdemo6.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Second_Welcome_FragmentOne extends Fragment {


    public Second_Welcome_FragmentOne() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second__welcome__fragment_one, null);
        return view;
    }

}
