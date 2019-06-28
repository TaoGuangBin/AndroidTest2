package com.jiyun.qmtest.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jiyun.qmtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdlyWelcomeOneFragment extends Fragment {


    public ThirdlyWelcomeOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thirdly_welcome_one, null);
        return view;
    }

}
