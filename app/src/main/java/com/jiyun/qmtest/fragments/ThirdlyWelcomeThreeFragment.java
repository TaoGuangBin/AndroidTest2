package com.jiyun.qmtest.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jiyun.qmtest.MainActivity;
import com.jiyun.qmtest.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThirdlyWelcomeThreeFragment extends Fragment implements View.OnClickListener {


    private View view;
    /**
     * 进入
     */
    private Button mTwcThreefBt;

    public ThirdlyWelcomeThreeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thirdly_welcome_three, null);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mTwcThreefBt = (Button) view.findViewById(R.id.twc_threef_bt);
        mTwcThreefBt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.twc_threef_bt:
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
