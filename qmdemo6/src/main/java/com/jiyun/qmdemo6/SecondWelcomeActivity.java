package com.jiyun.qmdemo6;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.jiyun.qmdemo6.adapers.SecondWelcomeAdaper;
import com.jiyun.qmdemo6.fragments.SecondWelcomeFragmentTwo;
import com.jiyun.qmdemo6.fragments.Second_Welcome_FragmentOne;

import java.util.ArrayList;

public class SecondWelcomeActivity extends AppCompatActivity {

    private ViewPager mWcsVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_welcome);
        initView();
        initWcsVp();
    }

    private void initWcsVp() {
        FragmentManager fm = getSupportFragmentManager();
        ArrayList<Fragment> fl = new ArrayList<>();
        Second_Welcome_FragmentOne f1 = new Second_Welcome_FragmentOne();
        Second_Welcome_FragmentOne f2 = new Second_Welcome_FragmentOne();
        SecondWelcomeFragmentTwo f3 = new SecondWelcomeFragmentTwo();
        fl.add(f1);
        fl.add(f2);
        fl.add(f3);
        SecondWelcomeAdaper secondWelcomeAdaper = new SecondWelcomeAdaper(fm, fl);
        mWcsVp.setAdapter(secondWelcomeAdaper);

    }

    private void initView() {
        mWcsVp = (ViewPager) findViewById(R.id.wcs_vp);
    }
}
