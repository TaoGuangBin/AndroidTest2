package com.jiyun.qmdemo6.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiyun.qmdemo6.MainHomeActivity;
import com.jiyun.qmdemo6.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SecondWelcomeFragmentTwo extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * 确定
     */
    private Button mWcsBt;

    public SecondWelcomeFragmentTwo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_welcome_fragment_two, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mWcsBt = (Button) view.findViewById(R.id.wcs_bt);
        mWcsBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.wcs_bt:
                Intent intent = new Intent(getActivity(),MainHomeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
